package cn.rails.iServer.utils.workflow;


public class WorkItemList {

		//list
		private Object userTodoList;
		//总页数
		private int total=0;
		//总条数
		private int pageTotal=0;
		
		
		
		public Object getUserTodoList() {
			return userTodoList;
		}
		public void setUserTodoList(Object userTodoList) {
			this.userTodoList = userTodoList;
		}

		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}


		public int getPageTotal() {
			return pageTotal;
		}
		public void setPageTotal(int pageTotal) {
			this.pageTotal = pageTotal;
		}
	
		
		
}
