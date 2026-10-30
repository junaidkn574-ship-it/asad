package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthManager
import com.example.data.firestore.FirestoreHospitalRepository
import com.example.data.local.AppointmentEntity
import com.example.data.local.HospitalDatabase
import com.example.data.models.BloodStock
import com.example.data.models.Department
import com.example.data.models.Doctor
import com.example.data.models.HospitalData
import com.example.data.models.Testimonial
import com.example.data.repository.HospitalRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

enum class AppScreen {
    HOME,
    DEPARTMENTS,
    DOCTORS,
    APPOINTMENT,
    EMERGENCY,
    CONTACT
}

data class BookingFormState(
    val patientName: String = "",
    val patientPhone: String = "",
    val patientAge: String = "",
    val patientGender: String = "Male",
    val selectedDepartmentId: String = "cardiology",
    val selectedDoctorId: String = "doc_tariq",
    val selectedDate: String = "",
    val selectedTimeSlot: String = "10:30 AM",
    val symptoms: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
)

class HospitalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HospitalRepository
    private val firestoreRepository: FirestoreHospitalRepository

    init {
        val database = HospitalDatabase.getDatabase(application)
        repository = HospitalRepository(database.appointmentDao())
        firestoreRepository = FirestoreHospitalRepository(application)
    }

    // Auth State Flow
    val currentUser: StateFlow<FirebaseUser?> = AuthManager.authStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Firebase.auth.currentUser)

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Department Detail Dialog State
    private val _selectedDepartmentForDetail = MutableStateFlow<Department?>(null)
    val selectedDepartmentForDetail: StateFlow<Department?> = _selectedDepartmentForDetail.asStateFlow()

    // Doctor Detail Dialog State
    private val _selectedDoctorForDetail = MutableStateFlow<Doctor?>(null)
    val selectedDoctorForDetail: StateFlow<Doctor?> = _selectedDoctorForDetail.asStateFlow()

    // Doctor directory search & filter
    private val _doctorSearchQuery = MutableStateFlow("")
    val doctorSearchQuery: StateFlow<String> = _doctorSearchQuery.asStateFlow()

    private val _selectedDeptFilter = MutableStateFlow("all")
    val selectedDeptFilter: StateFlow<String> = _selectedDeptFilter.asStateFlow()

    // Department directory search
    private val _departmentSearchQuery = MutableStateFlow("")
    val departmentSearchQuery: StateFlow<String> = _departmentSearchQuery.asStateFlow()

    // Booking form state
    private val _bookingState = MutableStateFlow(
        BookingFormState(
            selectedDate = getDefaultDate()
        )
    )
    val bookingState: StateFlow<BookingFormState> = _bookingState.asStateFlow()

    // Confirmed booking modal state
    private val _lastConfirmedAppointment = MutableStateFlow<AppointmentEntity?>(null)
    val lastConfirmedAppointment: StateFlow<AppointmentEntity?> = _lastConfirmedAppointment.asStateFlow()

    // Blood Request Dialog
    private val _selectedBloodForRequest = MutableStateFlow<BloodStock?>(null)
    val selectedBloodForRequest: StateFlow<BloodStock?> = _selectedBloodForRequest.asStateFlow()

    // User message / toast banner
    private val _userNotice = MutableStateFlow<String?>(null)
    val userNotice: StateFlow<String?> = _userNotice.asStateFlow()

    // Local appointments flow
    val userAppointments: StateFlow<List<AppointmentEntity>> = repository.allAppointments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val departments: List<Department> = repository.getDepartments()
    val doctors: List<Doctor> = repository.getDoctors()
    val bloodStocks: List<BloodStock> = repository.getBloodStocks()
    val testimonials: List<Testimonial> = repository.getTestimonials()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun openDepartmentDetail(department: Department) {
        _selectedDepartmentForDetail.value = department
    }

    fun closeDepartmentDetail() {
        _selectedDepartmentForDetail.value = null
    }

    fun openDoctorDetail(doctor: Doctor) {
        _selectedDoctorForDetail.value = doctor
    }

    fun closeDoctorDetail() {
        _selectedDoctorForDetail.value = null
    }

    fun openBloodRequest(bloodStock: BloodStock) {
        _selectedBloodForRequest.value = bloodStock
    }

    fun closeBloodRequest() {
        _selectedBloodForRequest.value = null
    }

    fun clearNotice() {
        _userNotice.value = null
    }

    fun showNotice(msg: String) {
        _userNotice.value = msg
    }

    fun setDoctorSearchQuery(query: String) {
        _doctorSearchQuery.value = query
    }

    fun setSelectedDeptFilter(deptId: String) {
        _selectedDeptFilter.value = deptId
    }

    fun setDepartmentSearchQuery(query: String) {
        _departmentSearchQuery.value = query
    }

    fun initiateBookingForDoctor(doctor: Doctor) {
        _bookingState.value = _bookingState.value.copy(
            selectedDepartmentId = doctor.departmentId,
            selectedDoctorId = doctor.id,
            selectedTimeSlot = doctor.availableSlots.firstOrNull() ?: "10:00 AM",
            errorMessage = null
        )
        _selectedDoctorForDetail.value = null
        _currentScreen.value = AppScreen.APPOINTMENT
    }

    fun initiateBookingForDepartment(department: Department) {
        val firstDocInDept = doctors.firstOrNull { it.departmentId.equals(department.id, ignoreCase = true) }
        _bookingState.value = _bookingState.value.copy(
            selectedDepartmentId = department.id,
            selectedDoctorId = firstDocInDept?.id ?: "",
            selectedTimeSlot = firstDocInDept?.availableSlots?.firstOrNull() ?: "10:00 AM",
            errorMessage = null
        )
        _selectedDepartmentForDetail.value = null
        _currentScreen.value = AppScreen.APPOINTMENT
    }

    fun updatePatientName(name: String) {
        _bookingState.value = _bookingState.value.copy(patientName = name, errorMessage = null)
    }

    fun updatePatientPhone(phone: String) {
        _bookingState.value = _bookingState.value.copy(patientPhone = phone, errorMessage = null)
    }

    fun updatePatientAge(age: String) {
        _bookingState.value = _bookingState.value.copy(patientAge = age, errorMessage = null)
    }

    fun updatePatientGender(gender: String) {
        _bookingState.value = _bookingState.value.copy(patientGender = gender)
    }

    fun updateSelectedDepartment(deptId: String) {
        val docsForDept = doctors.filter { it.departmentId.equals(deptId, ignoreCase = true) }
        val newDocId = docsForDept.firstOrNull()?.id ?: ""
        val newSlot = docsForDept.firstOrNull()?.availableSlots?.firstOrNull() ?: "10:00 AM"
        _bookingState.value = _bookingState.value.copy(
            selectedDepartmentId = deptId,
            selectedDoctorId = newDocId,
            selectedTimeSlot = newSlot
        )
    }

    fun updateSelectedDoctor(docId: String) {
        val doc = doctors.find { it.id == docId }
        val newSlot = doc?.availableSlots?.firstOrNull() ?: _bookingState.value.selectedTimeSlot
        _bookingState.value = _bookingState.value.copy(
            selectedDoctorId = docId,
            selectedTimeSlot = newSlot
        )
    }

    fun updateSelectedDate(date: String) {
        _bookingState.value = _bookingState.value.copy(selectedDate = date)
    }

    fun updateSelectedTimeSlot(slot: String) {
        _bookingState.value = _bookingState.value.copy(selectedTimeSlot = slot)
    }

    fun updateSymptoms(symptoms: String) {
        _bookingState.value = _bookingState.value.copy(symptoms = symptoms)
    }

    fun submitBooking() {
        val state = _bookingState.value
        if (state.patientName.isBlank()) {
            _bookingState.value = state.copy(errorMessage = "Please enter patient full name")
            return
        }
        if (state.patientPhone.isBlank() || state.patientPhone.length < 8) {
            _bookingState.value = state.copy(errorMessage = "Please enter a valid phone number (e.g. 03451234567)")
            return
        }
        if (state.patientAge.isBlank()) {
            _bookingState.value = state.copy(errorMessage = "Please enter patient age")
            return
        }

        val dept = departments.find { it.id == state.selectedDepartmentId }
        val doc = doctors.find { it.id == state.selectedDoctorId }
        val deptName = dept?.name ?: "General OPD"
        val docName = doc?.name ?: "On-duty Specialist"

        viewModelScope.launch {
            _bookingState.value = state.copy(isSubmitting = true)
            val newAppt = repository.bookAppointment(
                patientName = state.patientName,
                patientPhone = state.patientPhone,
                patientAge = state.patientAge,
                patientGender = state.patientGender,
                departmentId = state.selectedDepartmentId,
                departmentName = deptName,
                doctorId = state.selectedDoctorId,
                doctorName = docName,
                appointmentDate = state.selectedDate.ifBlank { getDefaultDate() },
                timeSlot = state.selectedTimeSlot,
                symptoms = state.symptoms
            )

            // If user is authenticated, also sync to Firestore cloud database
            val user = currentUser.value
            if (user != null) {
                try {
                    firestoreRepository.bookAppointment(
                        userId = user.uid,
                        patientName = state.patientName,
                        patientPhone = state.patientPhone,
                        patientAge = state.patientAge,
                        patientGender = state.patientGender,
                        departmentId = state.selectedDepartmentId,
                        departmentName = deptName,
                        doctorId = state.selectedDoctorId,
                        doctorName = docName,
                        appointmentDate = state.selectedDate.ifBlank { getDefaultDate() },
                        timeSlot = state.selectedTimeSlot,
                        symptoms = state.symptoms
                    )
                } catch (_: Exception) {
                    // Handled in repository with logging
                }
            }

            _bookingState.value = BookingFormState(
                selectedDate = getDefaultDate(),
                selectedDepartmentId = state.selectedDepartmentId,
                selectedDoctorId = state.selectedDoctorId
            )
            _lastConfirmedAppointment.value = newAppt
            val syncNotice = if (user != null) " (Synced to Cloud)" else ""
            _userNotice.value = "Appointment confirmed! Token: ${newAppt.tokenNumber}$syncNotice"
        }
    }

    fun dismissConfirmedAppointment() {
        _lastConfirmedAppointment.value = null
    }

    fun cancelAppointment(id: Long) {
        viewModelScope.launch {
            repository.cancelAppointment(id)
            _userNotice.value = "Appointment cancelled."
        }
    }

    fun deleteAppointment(id: Long) {
        viewModelScope.launch {
            repository.deleteAppointment(id)
            _userNotice.value = "Appointment record removed."
        }
    }

    fun submitInquiry(name: String, contact: String, message: String) {
        val user = currentUser.value
        if (user != null) {
            viewModelScope.launch {
                try {
                    firestoreRepository.submitInquiry(user.uid, name, contact, message)
                    _userNotice.value = "Inquiry saved and synced to Central Hospital cloud."
                } catch (_: Exception) {
                }
            }
        }
    }

    fun submitBloodRequest(bloodGroup: String) {
        val user = currentUser.value
        if (user != null) {
            viewModelScope.launch {
                try {
                    firestoreRepository.submitBloodRequest(user.uid, bloodGroup)
                    _userNotice.value = "Blood request for $bloodGroup recorded on cloud database."
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun getDefaultDate(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 1)
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun getUpcomingDates(): List<String> {
        val list = mutableListOf<String>()
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        val cal = Calendar.getInstance()
        list.add("Today (" + SimpleDateFormat("dd MMM", Locale.getDefault()).format(cal.time) + ")")
        for (i in 1..6) {
            cal.add(Calendar.DAY_OF_YEAR, 1)
            list.add(sdf.format(cal.time))
        }
        return list
    }
}
