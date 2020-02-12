package objects;

import java.util.Date;

public class SlotOperation {
		Employee employee;
		Vehicle vehicle ;
		ParkingSlot slot;
		Date bookingDate ;
		Date cancelllingDate ;
	
		public SlotOperation( Employee employee, Vehicle vehicle, ParkingSlot slot, Date bookingDate, Date cancelllingDate) {
			this.employee = employee;
			this.vehicle = vehicle;
			this.slot = slot;
			this.bookingDate = bookingDate;
			this.cancelllingDate = cancelllingDate;
		}
		
		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public Vehicle getVehicle() {
			return vehicle;
		}

		public void setVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
		}

		public ParkingSlot getSlot() {
			return slot;
		}

		public void setSlot(ParkingSlot slot) {
			this.slot = slot;
		}

		public Date getBookingDate() {
			return bookingDate;
		}

		public void setBookingDate(Date bookingDate) {
			this.bookingDate = bookingDate;
		}

		public Date getCancelllingDate() {
			return cancelllingDate;
		}

		public void setCancelllingDate(Date cancelllingDate) {
			this.cancelllingDate = cancelllingDate;
		}

		@Override
		public String toString() {
			return "SlotOperation [employee=" + employee + ", vehicle=" + vehicle + ", slot=" + slot + ", bookingDate="
					+ bookingDate + ", cancelllingDate=" + cancelllingDate + "]";
		}

		
		
}
