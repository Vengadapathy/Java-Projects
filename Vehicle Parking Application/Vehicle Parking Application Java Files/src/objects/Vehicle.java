package objects;

public class Vehicle {
		int vehicleNo ;
		String vehicleName ;
		String vehicleType;
		String vehicleColour ;
		Employee employee;
		
		
		public Vehicle(int vehicleNo, String vehicleName, String vehicleType, String vehicleColour, Employee employee) {
			this.vehicleNo = vehicleNo;
			this.vehicleName = vehicleName;
			this.vehicleType = vehicleType;
			this.vehicleColour = vehicleColour;
			this.employee = employee;
		}
		public int getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(int vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public String getVehicleName() {
			return vehicleName;
		}
		public void setVehicleName(String vehicleName) {
			this.vehicleName = vehicleName;
		}
		public String getVehicleType() {
			return vehicleType;
		}
		public void setVehicleType(String vehicleType) {
			this.vehicleType = vehicleType;
		}
		public String getVehicleColour() {
			return vehicleColour;
		}
		public void setVehicleColour(String vehicleColour) {
			this.vehicleColour = vehicleColour;
		}
		public Employee getEmployee() {
			return employee;
		}
		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
}
