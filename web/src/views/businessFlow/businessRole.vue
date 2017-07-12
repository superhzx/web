<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <div class="filter-item">
        <label class="el-form-item__label">
          业务角色名称
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

      <el-table-column align="center" label="业务角色名称">
        <template scope="scope">
          <span>{{scope.row.businessRoleName}}</span>
        </template>
      </el-table-column>

      <el-table-column label="业务角色编码">
        <template scope="scope">
          <span>{{scope.row.code}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="业务系统名称">
        <template scope="scope">
          <span>{{scope.row.systemName}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="描述">
        <template scope="scope">
          <span>{{scope.row.memo}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="同步标识">
        <template scope="scope">
          <span>{{scope.row.synFlag | synFlagHandler}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template scope="scope">
          <el-button size="small" type="success text" icon="search" title="查看" @click="handleDetail(scope.row)">
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
        <el-form-item label="业务角色名称">
          <el-input v-model="temp.businessRoleName" :readonly="dialogStatus==='detail'"
                    placeholder="请输入业务角色名称"></el-input>
        </el-form-item>
        <el-form-item label="业务角色编码">
          <el-input v-model="temp.code" :readonly="dialogStatus==='detail'" placeholder="请输入业务角色编码"></el-input>
        </el-form-item>
        <el-form-item label="业务系统名称">
          <el-select v-model="temp.systemId" :disabled="dialogStatus==='detail'" placeholder="请选择">
            <el-option v-for="item in commonData" :key="item.id" :label="item.businessSystemName"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" :readonly="dialogStatus==='detail'"  :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入内容" v-model="temp.memo">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dialogStatus!=='detail'" @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus!=='detail'" type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript">
  import moment from 'moment';
  import {
    businessRoleListByPage,
    businessRoleSaveOrUpdate,
    businessRoleDelete,
    businessSystemCommonData
  } from '../../api/businessFlow';
  export default {
    name: 'table_demo',
    data() {
      return {
        list: [],
        total: 0,
        listLoading: true,
        dialogLoading: false,
        listQuery: {
          page: 1,
          rows: 10,
          sidx: 'id', //排序字段
          sord: 'asc', // 规则 desc asc
          name: '',
        },
        temp: {
          id: '',
          businessRoleName: '',
          code: '',
          systemName: '',
          memo: ''
        },
        commonData: undefined,
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
      synFlagHandler(synFlag) {
        // 1同步成功 2新增失败 3更新失败 4删除失败
        switch(synFlag){
          case '1': synFlag = '同步成功'; break;
          case '2': synFlag = '新增失败'; break;
          case '3': synFlag = '更新失败'; break;
          case '4': synFlag = '删除失败'; break;
        }
        return synFlag
      }
    },
    methods: {
      // 获取列表
      getList() {
        this.listLoading = true;
        businessRoleListByPage(this.listQuery).then(response => {
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
        this.dialogLoading = true;
        businessSystemCommonData()
          .then(res => {
            this.commonData = res.data.plist || [];
          })
          .catch(error => {
            this.commonData = [];
            this.$message({
              showClose: true,
              message: '获取数据失败',
              type: 'error'
            });
          })
          .then(() => {
            this.dialogLoading = false;
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
        this.getCommonData();
        this.resetTemp();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleDetail(row) {
        this.getCommonData();
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'detail';
        this.dialogFormVisible = true;
      },
      handleUpdate(row) {
        this.getCommonData();
        this.temp = Object.assign({}, row);
        this.dialogStatus = 'update';
        this.dialogFormVisible = true;
      },
      // 单条删除
      deleteCurrent(row) {
        let type = 'error';
        if(row.createDate){
          row.createDate = moment(row.createDate).format("YYYY-MM-DD HH:mm:ss");
        }
        businessRoleDelete(row)
          .then((res) => {
            if (res.data.status_code === 0) {
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
          if(item.createDate){
            item.createDate = moment(item.createDate).format("YYYY-MM-DD HH:mm:ss");
          }
          bachAPI.push(businessRoleDelete(item));
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
        if(this.temp.createDate){
          this.temp.createDate = moment(this.temp.createDate).format("YYYY-MM-DD HH:mm:ss");
        }
        businessRoleSaveOrUpdate(this.temp)
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
          businessRoleName: '',
          code: '',
          systemId: '',
          memo: ''
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
</style>
