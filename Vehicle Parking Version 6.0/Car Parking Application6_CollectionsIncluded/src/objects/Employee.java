package objects;

public class Employee {
		String empName;
		int empID ;
		long empPhNo ;
		String mailID ;
		String teamName ;
		String empSeatNo ;
		String empBlockName ;
		
		public Employee(String empName, int empID, long empPhNo, String mailID, String teamName, String empSeatNo, String empBlockName) {
			this.empName = empName;
			this.empID = empID;
			this.empPhNo = empPhNo;
			this.mailID = mailID;
			this.teamName = teamName;
			this.empSeatNo = empSeatNo;
			this.empBlockName = empBlockName;
		}
		public String getEmpName() {
			return empName;
		}
		public void setEmpName(String empName) {
			this.empName = empName;
		}
		public int getEmpID() {
			return empID;
		}
		public void setEmpID(int empID) {
			this.empID = empID;
		}
		public long getEmpPhNo() {
			return empPhNo;
		}
		public void setEmpPhNo(long empPhNo) {
			this.empPhNo = empPhNo;
		}
		public String getMailID() {
			return mailID;
		}
		public void setMailID(String mailID) {
			this.mailID = mailID;
		}
		public String getTeamName() {
			return teamName;
		}
		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}
		public String getEmpSeatNo() {
			return empSeatNo;
		}
		public void setEmpSeatNo(String empSeatNo) {
			this.empSeatNo = empSeatNo;
		}
		public String getEmpBlockName() {
			return empBlockName;
		}
		public void setEmpBlockName(String empBlockName) {
			this.empBlockName = empBlockName;
		}
		@Override
		public String toString() {
			return "Employee [empName=" + empName + ", empID=" + empID + ", empPhNo=" + empPhNo + ", mailID=" + mailID
					+ ", teamName=" + teamName + ", empSeatNo=" + empSeatNo + ", empBlockName=" + empBlockName + "]";
		}
		
		
}
