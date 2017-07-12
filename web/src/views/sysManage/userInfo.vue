<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <div class="filter-item">
        <label class="el-form-item__label">
          用户姓名
          <el-input style="width: 300px;" placeholder="输入姓名"
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
      <el-table-column type="index" align="center" label="序号" width="65">
      </el-table-column>

      <el-table-column align="center" label="工号" width="100">
        <template scope="scope">
          <span>{{scope.row.code}}</span>
        </template>
      </el-table-column>
      
      <el-table-column align="center" label="姓名" width="100">
        <template scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>

      <el-table-column label="角色">
        <template scope="scope">
          <span>{{scope.row.roleName}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="岗位">
        <template scope="scope">
          <span>{{scope.row.positionName}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="备注">
        <template scope="scope">
          <span>{{scope.row.memo}}</span>
        </template>
      </el-table-column>
      
      <el-table-column align="center" width="65" label="同步标识">
        <template scope="scope">
          <span>{{scope.row.synFlag}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="220">
        <template scope="scope">
          <el-button size="small" type="success" icon="search" title="查看" @click="handleDetail(scope.row)">
          </el-button>
          <el-button size="small" type="primary" icon="edit" title="编辑" @click="handleUpdate(scope.row)">
          </el-button>
          <el-button size="small" type="warning" icon="setting " title="设置岗位" @click="setPosition(scope.row)">
          </el-button>
          <el-button size="small" type="primary" icon="setting " title="设置角色" @click="setRole(scope.row)">
          </el-button>
          <el-button size="small" type="danger" icon="delete" title="删除" @click="deleteCurrent(scope.row)">
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" style="min-width: 400px;">
      <el-form class="small-space" :model="temp" label-width="120px"
               style='margin-left:50px;'>
        <el-form-item label="工号">
          <el-input v-model="temp.code" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="temp.pwd" :readonly="dialogStatus==='detail'"  type="password" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="temp.name" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="temp.sex" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="temp.cardNo" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="temp.phone" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="temp.mobile" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="temp.email" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="序号">
          <el-input v-model="temp.sortNumber" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="temp.memo" :readonly="dialogStatus==='detail'" placeholder=""></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogStatus!=='detail'" @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus!=='detail'" type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    
     <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogPositionVisible">
      <div class="dialog-content">
        <el-card class="card-left">
          <div slot="header" class="clearfix">
            <span>岗位列表</span>
          </div>
          <el-table
            :data="positionList"
            border
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="岗位名称">
            </el-table-column>
            <el-table-column label="设置主岗">
	            <template scope="scope">
		          <el-button size="small" type="primary" icon="setting " @click="">
		          </el-button>
	        	</template>
            </el-table-column>
          </el-table>
          <el-pagination
            small
            layout="prev, pager, next"
            :total="positionListTotal">
          </el-pagination>
          <!--<el-pagination @size-change="" @current-change=""-->
          <!--:current-page.sync="unselectQuery.page"-->
          <!--:page-sizes="[10,20,30, 50]" :page-size="unselectQuery.limit"-->
          <!--layout="total, sizes, prev, pager, next, jumper" :total="unselectTotal">-->
          <!--</el-pagination>-->
        </el-card>
        <div class="card-middle">
          <el-button class="card-middle-item" type="primary" icon="d-arrow-right"></el-button>
          <el-button class="card-middle-item" type="primary" icon="d-arrow-left"></el-button>
        </div>
        <el-card class="card-right">
          <div slot="header" class="clearfix">
            <span>已选择列表</span>
          </div>
          <el-table
            :data="selectedPositionList"
            border
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="岗位名称">
            </el-table-column>
            <el-table-column
              prop="name"
              label="是否主岗">
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer clearfix">
        <el-button @click="dialogPersonFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="">确 定</el-button>
      </div>
     </el-dialog>
     
     <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogRoleVisible">
      <div class="dialog-content">
        <el-card class="card-left">
          <div slot="header" class="clearfix">
            <span>角色列表</span>
          </div>
          <el-table
            :data="roleList"
            border
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="角色名称">
            </el-table-column>
            <el-table-column
              prop="name"
              label="备注">
            </el-table-column>
          </el-table>
          <el-pagination
            small
            layout="prev, pager, next"
            :total="roleListTotal">
          </el-pagination>
          <!--<el-pagination @size-change="" @current-change=""-->
          <!--:current-page.sync="unselectQuery.page"-->
          <!--:page-sizes="[10,20,30, 50]" :page-size="unselectQuery.limit"-->
          <!--layout="total, sizes, prev, pager, next, jumper" :total="unselectTotal">-->
          <!--</el-pagination>-->
        </el-card>
        <div class="card-middle">
          <el-button class="card-middle-item" type="primary" icon="d-arrow-right"></el-button>
          <el-button class="card-middle-item" type="primary" icon="d-arrow-left"></el-button>
        </div>
        <el-card class="card-right">
          <div slot="header" class="clearfix">
            <span>已选择列表</span>
          </div>
          <el-table
            :data="selectedRoleList"
            border
            tooltip-effect="dark"
            style="width: 100%"
            height="260"
          >
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column
              prop="name"
              label="角色名称">
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer clearfix">
        <el-button @click="dialogPersonFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="">确 定</el-button>
      </div>
     </el-dialog>
  </div>
</template>

<script type="text/ecmascript">
  import {
    listByPage,
    saveUser,
    deleteUser
  } from '../../api/sysManage/userInfo';
  export default {
    name: 'table_demo',
    data() {
      return {
        positionList:[],
        positionListTotal:0,
        selectedPositionList:[],
        roleList:[],
        roleListTotal:0,
        selectedRoleList:[],
        list: [],
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          rows: 10,
          sidx: 'id', //排序字段
          sord: 'asc', // 规则 desc asc
          name: '',
        },
        temp: {
          id: '',
          name: '',
          code: '',
          roleName: '',
          positionName:'',
          memo:'',
          synFlag:''
        },
        dialogFormVisible: false,
        dialogPositionVisible: false,
        dialogRoleVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑人员信息',
          create: '添加人员信息',
          detail: '查看人员信息',
          position: '设置人员岗位',
          role: '设置人员角色'
        },
        dialogPvVisible: false,
        tableKey: 0,
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
      }
    },
    methods: {
      // 获取列表
      getList() {
        this.listLoading = true;
        listByPage(this.listQuery).then(response => {
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
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleDetail(row) {
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'detail';
        this.dialogFormVisible = true;
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'update';
        this.dialogFormVisible = true;
      },
      setPosition(row) {
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'position';
        this.dialogPositionVisible = true;
      },
      setRole(row) {
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'role';
        this.dialogRoleVisible = true;
      },
      // 单条删除
      deleteCurrent(row) {
        let type = 'error';
        deleteUser(row)
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
          bachAPI.push(businessConfigDelete(item));
        });
        this.$axios.all(bachAPI)
          .then(reses => {
            reses.forEach((item, index) => {
              if (item.data.status_code === 0) {
                successInfo += datas[index].workflowCode + ';';
              } else {
                failInfo += data[index].workflowCode + ';';
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
        saveUser(this.temp)
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
          workflowCode: '',
          workflowName: '',
          url: ''
        };
      },
      setSelection(selection, row) {
        this.selections = selection;
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

