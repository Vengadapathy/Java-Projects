package objects;

import java.util.Date;

public class SlotOperation {
		int operationid;
		Employee employee;
		ParkingSlot slot;
		String slotoperationType ;
		Date operationDate ;
	
		public SlotOperation(int operationid, Employee employee, ParkingSlot slot, String slotoperationType,Date date) {
			this.operationid = operationid;
			this.employee = employee;
			this.slot = slot;
			this.slotoperationType = slotoperationType;
			this.operationDate = date;
		}

		public int getOperationid() {
			return operationid;
		}

		public void setOperationid(int operationid) {
			this.operationid = operationid;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public ParkingSlot getSlot() {
			return slot;
		}

		public void setSlot(ParkingSlot slot) {
			this.slot = slot;
		}

		public String getSlotoperationType() {
			return slotoperationType;
		}

		public void setSlotoperationType(String slotoperationType) {
			this.slotoperationType = slotoperationType;
		}

		public Date getOperationDate() {
			return operationDate;
		}

		public void setOperationDate(Date operationDate) {
			this.operationDate = operationDate;
		}
		
		
		
}
