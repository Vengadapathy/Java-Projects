package SQL.operations;

public interface SQL_OpeartionsInterface {
	
		public static final String SET = "SET";
		public static final String UPDATE ="UPDATE";
		public static final String CREATE="create";
		public static final String ALTER="alter";
		public static final String DELETE="delete";
		public static final String SELECT ="select";
		
		public String create();
		public String select();
		public String update() ;
		public String delete();
}
