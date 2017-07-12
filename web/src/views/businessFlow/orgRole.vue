<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <div class="filter-item">
        <label class="el-form-item__label">
          组织角色名称
          <el-input style="width: 300px;" placeholder="标题"
                    v-model="listQuery.name">
            <el-button slot="append" icon="search" @click="handleFilter">搜索</el-button>
          </el-input>
        </label>
      </div>
      <div class="tool-bar">
        <div class="tool-bar-item">
          <el-button @click="handleCreate" type="small" icon="edit">添加
          </el-button>
          <el-button @click="" type="small" icon="delete" @click="batchDelete">删除
          </el-button>
        </div>
      </div>
    </div>
    <el-table :key='tableKey' :data="list" v-loading.body="listLoading" border fit highlight-current-row
              @selection-change="setSelection"
              style="width: 100%">
      <el-table-column
        type="selection"
        width="55" label="全选">
      </el-table-column>
      <el-table-column type="index" label="序号" width="65">
      </el-table-column>

      <el-table-column align="center" label="组织角色名称">
        <template scope="scope">
          <span>{{scope.row.orgRoleName}}</span>
        </template>
      </el-table-column>

      <el-table-column label="组织角色编码">
        <template scope="scope">
          <span>{{scope.row.businessRoleCode}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="部门名称">
        <template scope="scope">
          <span>{{scope.row.departmentName}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="备注">
        <template scope="scope">
          <span>{{scope.row.memo}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="同步标识">
        <template scope="scope">
          <span>{{scope.row.synFlag | synFlagHandler}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200">
        <template scope="scope">
          <el-button size="small" type="success text" icon="search" title="查看" @click="handleDetail(scope.row)">
          </el-button>
          <el-button size="small" type="warning text" icon="setting" title="设置" @click="handleSetPerson(scope.row)">
          </el-button>
          <el-button size="small" type="primary text" icon="edit" title="编辑" @click="handleUpdate(scope.row)">
          </el-button>
          <el-button size="small" type="danger text" icon="delete" title="删除" @click="deleteCurrent(scope.row)">
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page.sync="listQuery.page"
                     :page-sizes="[10,20,30, 50]" :page-size="listQuery.limit"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" v-loading.body="dialogLoading" :visible.sync="dialogFormVisible"
               style="min-width: 400px;">
      <el-form class="small-space" :model="temp" label-width="120px"
               style='margin-left:50px;'>
        <el-form-item label="组织角色名称">
          <el-input v-model="temp.orgRoleName" :readonly="dialogStatus==='detail'"
                    placeholder="请输入组织角色名称"></el-input>
        </el-form-item>
        <el-form-item label="组织角色编码">
          <el-input v-model="temp.businessRoleCode" :readonly="dialogStatus==='detail'"
                    placeholder="请输入组织角色编码"></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <v-select-tree v-model="temp.departmentCode" ref="departmentTree" :disabled="dialogStatus==='detail'"
                         :treeData="commonData.departments" :propNames="defaultProps">
          </v-select-tree>
        </el-form-item>
        <el-form-item label="业务角色">
          <el-select v-model="temp.businessRoleId" :disabled="dialogStatus==='detail'" placeholder="请选择">
            <el-option v-for="item in commonData.businessRole" :key="item.id" :label="item.businessRoleName"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" :readonly="dialogStatus==='detail'" :autosize="{ minRows: 2, maxRows: 4}"
                    placeholder="请输入内容" v-model="temp.memo">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogStatus!=='detail'" @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus!=='detail'" type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="选择用户" v-loading.body="dialogPersonLoading" :visible.sync="dialogPersonFormVisible">
      <div class="dialog-filter-container">
        <div class="dialog-filter-item">
          <label class="dialog-filter-label">
            用户姓名
            <el-input style="width: 150px;" placeholder="用户名称"
                      v-model="unselectQuery.name">
            </el-input>
          </label>
          <label class="dialog-filter-label">
            用户编号
            <el-input style="width: 150px;" placeholder="用户编号"
                      v-model="unselectQuery.code">
            </el-input>
          </label>
          <el-button type="primary" size="small" icon="search" @click="personSearch">搜索</el-button>
        </div>
      </div>
      <div class="dialog-content">
        <el-card class="card-left">
          <div slot="header" class="clearfix">
            <span>用户列表</span>
          </div>
          <el-table
            :data="personList"
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
            @selection-change="setLeftPerson"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名">
            </el-table-column>
            <el-table-column
              prop="code"
              label="用户编号">
            </el-table-column>
          </el-table>
          <el-pagination
            @current-change="handlePersonPage"
            small
            layout="prev, pager, next"
            :total="personListTotal">
          </el-pagination>
          <!--<el-pagination @size-change="" @current-change=""-->
          <!--:current-page.sync="unselectQuery.page"-->
          <!--:page-sizes="[10,20,30, 50]" :page-size="unselectQuery.limit"-->
          <!--layout="total, sizes, prev, pager, next, jumper" :total="unselectTotal">-->
          <!--</el-pagination>-->
        </el-card>
        <div class="card-middle">
          <el-button class="card-middle-item" type="primary" icon="d-arrow-right" @click="addPerson"></el-button>
          <el-button class="card-middle-item" type="primary" icon="d-arrow-left" @click="delPerson"></el-button>
        </div>
        <el-card class="card-right">
          <div slot="header" class="clearfix">
            <span>已选择用户列表</span>
          </div>
          <el-table
            :data="selectedPersonList"
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
            @selection-change="setRightPerson"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名">
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer clearfix">
        <el-button @click="dialogPersonFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="personSave">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript">
  import moment from 'moment';
  import {
    orgRoleListByPage,
    orgRoleSaveOrUpdate,
    orgRoleDelete,
    orgRoleCommonData,
    personQueryByPage,
    orgRoleUserSave
  } from '../../api/businessFlow';
  import SelectTree from '../../components/selectTree';
  import { treeData, synFlagHandler } from  '../../common/js/utils';
  import { Loading } from 'element-ui';
  export default {
    name: 'table_demo',
    components: {
      'v-select-tree': SelectTree
    },
    data() {
      return {
        personList: [],
        selectedPersonList: [],
        leftPersonList: [],
        rightPersonList: [],
        defaultProps: {
          children: 'children',
          label: 'name',
          id: 'id',
        },
        list: [],
        total: 0,
        personListTotal: 0,
        listLoading: true,
        dialogPersonLoading: false,
        dialogPersonFormVisible: false,
        dialogLoading: false,
        // 组织列表查询条件
        listQuery: {
          page: 1,
          rows: 10,
          sidx: 'id', //排序字段
          sord: 'asc', // 规则 desc asc
          name: '',
        },
        // 人员列表查询条件
        unselectQuery: {
          page: 1,
          rows: 10,
          sidx: 'id',
          sord: 'asc',
          name: '',
          code: '',
          params: '',
        },
        // 已选择人员信息及 组织信息
        selectedPersonSaveParam: {
          roleId: '', // row.id
          roleCode: '', // row.code
          ids: '',
          codes: ''
        },
        // 组织信息表单绑定
        temp: {
          id: '',
          orgRoleName: '',
          departmentCode: '',
          businessRoleCode: '',
          departmentId: '',
          businessRoleId: '',
          memo: ''
        },
        // 下拉框与下拉树数据
        commonData: {
          departments: [],
          businessRole: []
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑业务角色',
          create: '添加业务角色',
          detail: '查看业务角色'
        },
        dialogPvVisible: false,
        tableKey: 1,
        selections: []
      };
    },
    created() {
      this.getList();
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          success: 'success',
          fail: 'danger'
        };
        return statusMap[status];
      },
      synFlagHandler
    },
    methods: {
      // 获取列表
      getList() {
        this.listLoading = true;
        orgRoleListByPage(this.listQuery).then(response => {
          this.list = response.data.rows;
          this.total = response.data.records;
          this.listLoading = false;
        }).catch(error => {
          this.$message({
            showClose: true,
            message: '获取数据失败',
            type: 'error'
          });
          this.listLoading = false;
        });
      },
      // 获取下拉框信息
      getCommonData() {
        let loadingInstance = Loading.service({fullscreen: true});
//        this.dialogLoading = true;
        return orgRoleCommonData()
          .then(this.$axios.spread((dep, org) => {
            let deptTreeData = treeData({
              idKey: 'id',
              pIdKey: 'pId',
              children: 'children'
            }, dep.data);
            this.$set(this.commonData, 'departments', deptTreeData);
            this.$set(this.commonData, 'businessRole', org.data.plist || []);
          }))
          .catch(error => {
            this.commonData = [];
            this.$message({
              showClose: true,
              message: '获取数据失败',
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
//            this.dialogLoading = false;
          });
      },
      handleFilter() {
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleCreate() {
        this.resetTemp();
        this.getCommonData();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleDetail(row) {
        this.getCommonData()
          .then(() => {
            this.temp = Object.assign({}, row);
            this.dialogStatus = 'detail';
            this.dialogFormVisible = true;
          });
      },
      handleUpdate(row) {
        this.getCommonData()
          .then(() => {
            this.temp = Object.assign({}, row);
            this.dialogStatus = 'update';
            this.dialogFormVisible = true;
          });
      },
      // 单条删除
      deleteCurrent(row) {
        let type = 'error';
        if (row.createDate) {
          row.createDate = moment(row.createDate).format('YYYY-MM-DD HH:mm:ss');
        }
        orgRoleDelete(row)
          .then((res) => {
            if (res.data.status_code === 1) {
              type = 'success';
              this.getList();
            }
            this.$notify({
              title: type,
              message: res.data.msg,
              type: type,
              duration: 2000
            });
          })
          .catch(error => {
            this.$notify({
              title: 'error',
              message: error,
              type: 'error',
              duration: 2000
            });
          });
      },
      // 批量删除
      batchDelete() {
        let type = 'error';
        let bachAPI = [];
        let successInfo = '删除成功编码：';
        let failInfo = '删除失败编码：';
        let datas = this.selections;
        this.selections.forEach(item => {
          if (item.createDate) {
            item.createDate = moment(item.createDate).format('YYYY-MM-DD HH:mm:ss');
          }
          bachAPI.push(orgRoleDelete(item));
        });
        this.$axios.all(bachAPI)
          .then(reses => {
            reses.forEach((item, index) => {
              if (item.data.status_code === 0) {
                successInfo += datas[index].code + ';';
              } else {
                failInfo += data[index].code + ';';
              }
            });
            this.getList();
            this.$notify({
              title: 'info',
              message: successInfo + failInfo,
              type: 'info',
              duration: 2000
            });
          });
      },
      // 保存
      save() {
        let msg = this.dialogStatus === 'create' ? '新增' : this.dialogStatus === 'update' ? '更新' : '';

        let type = 'success';
        if (this.temp.createDate) {
          this.temp.createDate = moment(this.temp.createDate).format('YYYY-MM-DD HH:mm:ss');
        }
        let currentNode = this.$refs.departmentTree.$refs.tree.data[0] || null;
        this.temp.departmentId = currentNode.departmentId;
        orgRoleSaveOrUpdate(this.temp)
          .then(res => {
            if (res.data.status_code === 1) {
              msg += '成功！';
              this.getList();
              this.dialogFormVisible = false;
            } else {
              msg += '失败！';
              type = 'error';
            }
          })
          .catch(err => {
            msg += '失败';
            type = 'error';
          })
          .then(() => {
            this.$message({
              showClose: true,
              message: msg,
              type: type
            });
          });
      },
      resetTemp() {
        this.temp = {
          id: '',
          orgRoleName: '',
          businessRoleCode: '',
          departmentId: '',
          businessRoleId: '',
          departmentCode: '',
          memo: ''
        };
      },
      setSelection(selection, row) {
        this.selections = selection;
      },
      /**
       * 用户设置开始
       */
      handleSetPerson(row) {
        this.dialogPersonFormVisible = true;
        this.personSearchReset();
        this.getPersonList(row);
      },
      getPersonList(row){
        this.selectedPersonSaveParam.roleId = row.id;
        this.selectedPersonSaveParam.roleCode = row.code;
        let loadingInstance = Loading.service({fullscreen: true});
        let userId = row.userId || '';
        let userName = row.userName || '';
        let userCode = row.userCode || '';
        userId = (userId.slice(userId.length - 1) === ',') ? userId.slice(0, -1) : userId;
        let userIds = userId === '' ? [] : userId.split(',');
        let userNames = userName === '' ? [] : userName.split(',');
        let userCodes = userCode === '' ? [] : userCode.split(',');
        this.selectedPersonList.splice(0, this.selectedPersonList.length);
        for (let i = 0; i < userIds.length; i++) {
          this.selectedPersonList.push({id: userIds[i], name: userNames[i], code: userCodes[i]});
        }
        userId = '(\'' + userId.replace(new RegExp(',', 'g'), '\',\'') + '\')';
        this.unselectQuery.params = userId;
        personQueryByPage(this.unselectQuery)
          .then(res => {
            this.personList = res.data.rows || [];
            this.personListTotal = res.data.records;
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      handlePersonPage(val) {
        let loadingInstance = Loading.service({fullscreen: true});
        this.unselectQuery.page = val;
        personQueryByPage(this.unselectQuery)
          .then(res => {
            this.personList = res.data.rows || [];
            this.personListTotal = res.data.records;
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      personSearch() {
        let loadingInstance = Loading.service({fullscreen: true});
        this.unselectQuery.page = 1;
        personQueryByPage(this.unselectQuery)
          .then(res => {
            this.personList = res.data.rows || [];
            this.personListTotal = res.data.records;
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      setLeftPerson(selection, row) {
        this.leftPersonList = selection;
      },
      setRightPerson(selection, row) {
        this.rightPersonList = selection;
      },
      addPerson() {
        let loadingInstance = Loading.service({fullscreen: true});
        this.selectedPersonList = this.selectedPersonList.concat(this.leftPersonList);
        let userIds = [];
        for (let i = 0; i < this.selectedPersonList.length; i++) {
          userIds.push(this.selectedPersonList[i].id);
        }
        this.unselectQuery.params = '(\'' + userIds.join('\',\'') + '\')';
        personQueryByPage(this.unselectQuery)
          .then(res => {
            this.personList = res.data.rows || [];
            this.personListTotal = res.data.records;
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      delPerson() {
        let loadingInstance = Loading.service({fullscreen: true});
        let ids = this.rightPersonList.map(item => {return item.id;});
        let userIds = [];
        this.selectedPersonList = this.selectedPersonList.filter(item => {
          if (ids.indexOf(item.id) === -1) {
            userIds.push(item.id);
            return item;
          }
        });
        this.unselectQuery.params = '(\'' + userIds.join('\',\'') + '\')';
        personQueryByPage(this.unselectQuery)
          .then(res => {
            this.personList = res.data.rows || [];
            this.personListTotal = res.data.records;
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      personSave() {
        let loadingInstance = Loading.service({fullscreen: true});
        let codes = [];
        let ids = this.selectedPersonList.map(item => {
          codes.push(item.code);
          return item.id;
        });
        this.selectedPersonSaveParam.ids = ids.join(',');
        this.selectedPersonSaveParam.codes = codes.join(',');
        orgRoleUserSave(this.selectedPersonSaveParam)
          .then(res => {
            let type = 'success';
            let msg = '';
            if (res.data.status_code === 1) {
              msg = '操作成功';
              this.dialogPersonFormVisible = false;
              this.getList();
            } else {
              type = 'error';
              msg = '操作失败';
            }
            this.$message({
              showClose: true,
              message: msg,
              type: type
            });
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: error,
              type: 'error'
            });
          })
          .then(() => {
            loadingInstance.close();
          });
      },
      personSearchReset() {
        this.unselectQuery = {
          page: 1,
          rows: 10,
          sidx: 'id',
          sord: 'asc',
          name: '',
          code: '',
          params: '',
        };
      }
    }
  };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="stylus" rel="stylesheet/stylus">
  .dialog-content
    display: flex
    flex-direction: row
    justify-content: space-between
    margin-top: 10px
    .card-left
      width: 1%;
      flex: auto
    .card-middle
      display: flex
      flex: 0 0 50px
      flex-direction: column
      justify-content: center
      .el-button
        & + .el-button
          margin: 10px 0 0
    .card-right
      width: 1%;
      flex: auto
    .el-card
      .el-card__header
        padding: 10px 20px
      .el-card__body
        padding: 0
</style>
