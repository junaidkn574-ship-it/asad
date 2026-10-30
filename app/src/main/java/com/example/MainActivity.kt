package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Emergency
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppointmentConfirmationDialog
import com.example.ui.components.BloodBankDialog
import com.example.ui.components.DepartmentDetailDialog
import com.example.ui.components.DoctorDetailDialog
import com.example.ui.components.EmergencyTopBar
import com.example.ui.components.HospitalHeader
import com.example.ui.screens.AppointmentScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.DepartmentsScreen
import com.example.ui.screens.DoctorsScreen
import com.example.ui.screens.EmergencyHubScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.EmergencyRedTertiary
import com.example.ui.theme.RehmanHospitalTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.HospitalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RehmanHospitalTheme {
                RehmanHospitalApp()
            }
        }
    }
}

@Composable
fun RehmanHospitalApp(
    viewModel: HospitalViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedDeptForDetail by viewModel.selectedDepartmentForDetail.collectAsState()
    val selectedDocForDetail by viewModel.selectedDoctorForDetail.collectAsState()
    val doctorSearchQuery by viewModel.doctorSearchQuery.collectAsState()
    val selectedDeptFilter by viewModel.selectedDeptFilter.collectAsState()
    val departmentSearchQuery by viewModel.departmentSearchQuery.collectAsState()
    val bookingState by viewModel.bookingState.collectAsState()
    val lastConfirmedAppt by viewModel.lastConfirmedAppointment.collectAsState()
    val selectedBloodStock by viewModel.selectedBloodForRequest.collectAsState()
    val userNotice by viewModel.userNotice.collectAsState()
    val userAppointments by viewModel.userAppointments.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val context = LocalContext.current
    var isFullScreen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isFullScreen) {
        val activity = context as? ComponentActivity
        activity?.window?.let { window ->
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            if (isFullScreen) {
                insetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                insetsController.hide(WindowInsetsCompat.Type.systemBars())
            } else {
                insetsController.show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    LaunchedEffect(userNotice) {
        userNotice?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearNotice()
        }
    }

    // Handle back button: return to Home screen if on another tab
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        viewModel.navigateTo(AppScreen.HOME)
    }

    Scaffold(
        contentWindowInsets = if (isFullScreen) WindowInsets(0, 0, 0, 0) else WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                EmergencyTopBar(
                    onEmergencyClick = { viewModel.navigateTo(AppScreen.EMERGENCY) }
                )
                HospitalHeader(
                    onAmbulanceClick = { viewModel.navigateTo(AppScreen.EMERGENCY) },
                    isFullScreen = isFullScreen,
                    onToggleFullScreen = { isFullScreen = !isFullScreen }
                )
            }
        },
        bottomBar = {
            HospitalBottomNavigation(
                currentScreen = currentScreen,
                appointmentsCount = userAppointments.size,
                onNavigate = { viewModel.navigateTo(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        departments = viewModel.departments,
                        doctors = viewModel.doctors,
                        testimonials = viewModel.testimonials,
                        currentUser = currentUser,
                        onAuthSuccess = { user ->
                            viewModel.showNotice("Signed in as ${user.displayName ?: user.email}")
                        },
                        onAuthError = { err ->
                            viewModel.showNotice(err)
                        },
                        onSignOutComplete = {
                            viewModel.showNotice("Signed out of Firebase account.")
                        },
                        onNavigateToAppointments = { viewModel.navigateTo(AppScreen.APPOINTMENT) },
                        onNavigateToEmergency = { viewModel.navigateTo(AppScreen.EMERGENCY) },
                        onNavigateToDoctors = { viewModel.navigateTo(AppScreen.DOCTORS) },
                        onNavigateToDepartments = { viewModel.navigateTo(AppScreen.DEPARTMENTS) },
                        onNavigateToContact = { viewModel.navigateTo(AppScreen.CONTACT) },
                        onDepartmentClick = { dept -> viewModel.openDepartmentDetail(dept) },
                        onDoctorClick = { doc -> viewModel.openDoctorDetail(doc) }
                    )
                }

                AppScreen.DEPARTMENTS -> {
                    DepartmentsScreen(
                        departments = viewModel.departments,
                        searchQuery = departmentSearchQuery,
                        onSearchQueryChange = { viewModel.setDepartmentSearchQuery(it) },
                        onDepartmentClick = { dept -> viewModel.openDepartmentDetail(dept) }
                    )
                }

                AppScreen.DOCTORS -> {
                    DoctorsScreen(
                        doctors = viewModel.doctors,
                        departments = viewModel.departments,
                        searchQuery = doctorSearchQuery,
                        selectedDeptFilter = selectedDeptFilter,
                        onSearchQueryChange = { viewModel.setDoctorSearchQuery(it) },
                        onDeptFilterChange = { viewModel.setSelectedDeptFilter(it) },
                        onBookDoctor = { doc -> viewModel.initiateBookingForDoctor(doc) },
                        onDoctorDetail = { doc -> viewModel.openDoctorDetail(doc) }
                    )
                }

                AppScreen.APPOINTMENT -> {
                    AppointmentScreen(
                        departments = viewModel.departments,
                        doctors = viewModel.doctors,
                        bookingState = bookingState,
                        userAppointments = userAppointments,
                        upcomingDates = viewModel.getUpcomingDates(),
                        onNameChange = { viewModel.updatePatientName(it) },
                        onPhoneChange = { viewModel.updatePatientPhone(it) },
                        onAgeChange = { viewModel.updatePatientAge(it) },
                        onGenderChange = { viewModel.updatePatientGender(it) },
                        onDepartmentChange = { viewModel.updateSelectedDepartment(it) },
                        onDoctorChange = { viewModel.updateSelectedDoctor(it) },
                        onDateChange = { viewModel.updateSelectedDate(it) },
                        onSlotChange = { viewModel.updateSelectedTimeSlot(it) },
                        onSymptomsChange = { viewModel.updateSymptoms(it) },
                        onSubmitBooking = { viewModel.submitBooking() },
                        onCancelAppointment = { viewModel.cancelAppointment(it) },
                        onDeleteAppointment = { viewModel.deleteAppointment(it) },
                        onSelectAppointmentToView = { viewModel.dismissConfirmedAppointment() }
                    )
                }

                AppScreen.EMERGENCY -> {
                    EmergencyHubScreen(
                        bloodStocks = viewModel.bloodStocks,
                        onSelectBloodStock = { viewModel.openBloodRequest(it) }
                    )
                }

                AppScreen.CONTACT -> {
                    ContactScreen(
                        onShowNotice = { viewModel.showNotice(it) }
                    )
                }
            }
        }
    }

    // Modal Dialogs
    selectedDeptForDetail?.let { dept ->
        DepartmentDetailDialog(
            department = dept,
            onDismiss = { viewModel.closeDepartmentDetail() },
            onBookInDepartment = { viewModel.initiateBookingForDepartment(it) }
        )
    }

    selectedDocForDetail?.let { doc ->
        DoctorDetailDialog(
            doctor = doc,
            onDismiss = { viewModel.closeDoctorDetail() },
            onBookClick = { viewModel.initiateBookingForDoctor(it) }
        )
    }

    lastConfirmedAppt?.let { appt ->
        AppointmentConfirmationDialog(
            appointment = appt,
            onDismiss = { viewModel.dismissConfirmedAppointment() },
            onViewBookings = {
                viewModel.dismissConfirmedAppointment()
                viewModel.navigateTo(AppScreen.APPOINTMENT)
            }
        )
    }

    selectedBloodStock?.let { stock ->
        BloodBankDialog(
            bloodStock = stock,
            onDismiss = { viewModel.closeBloodRequest() },
            onRequestSubmit = { msg -> viewModel.showNotice(msg) }
        )
    }
}

@Composable
fun HospitalBottomNavigation(
    currentScreen: AppScreen,
    appointmentsCount: Int,
    onNavigate: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = Modifier.testTag("hospital_bottom_nav")
    ) {
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onNavigate(AppScreen.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            modifier = Modifier.testTag("nav_home")
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.DEPARTMENTS,
            onClick = { onNavigate(AppScreen.DEPARTMENTS) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.DEPARTMENTS) Icons.Filled.MedicalServices else Icons.Outlined.MedicalServices,
                    contentDescription = "Departments"
                )
            },
            label = { Text("Depts", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            modifier = Modifier.testTag("nav_departments")
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.DOCTORS,
            onClick = { onNavigate(AppScreen.DOCTORS) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.DOCTORS) Icons.Filled.People else Icons.Outlined.People,
                    contentDescription = "Doctors"
                )
            },
            label = { Text("Doctors", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            modifier = Modifier.testTag("nav_doctors")
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.APPOINTMENT,
            onClick = { onNavigate(AppScreen.APPOINTMENT) },
            icon = {
                if (appointmentsCount > 0) {
                    BadgedBox(
                        badge = {
                            Badge { Text("$appointmentsCount") }
                        }
                    ) {
                        Icon(
                            imageVector = if (currentScreen == AppScreen.APPOINTMENT) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                            contentDescription = "Book Appointment"
                        )
                    }
                } else {
                    Icon(
                        imageVector = if (currentScreen == AppScreen.APPOINTMENT) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                        contentDescription = "Book Appointment"
                    )
                }
            },
            label = { Text("Book OPD", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            modifier = Modifier.testTag("nav_appointment")
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.EMERGENCY,
            onClick = { onNavigate(AppScreen.EMERGENCY) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.EMERGENCY) Icons.Filled.Emergency else Icons.Outlined.Emergency,
                    contentDescription = "Emergency",
                    tint = if (currentScreen == AppScreen.EMERGENCY) EmergencyRedTertiary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            label = {
                Text(
                    text = "Emergency",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentScreen == AppScreen.EMERGENCY) EmergencyRedTertiary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = EmergencyRedTertiary.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("nav_emergency")
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.CONTACT,
            onClick = { onNavigate(AppScreen.CONTACT) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == AppScreen.CONTACT) Icons.Filled.PhoneInTalk else Icons.Outlined.Call,
                    contentDescription = "Contact"
                )
            },
            label = { Text("Contact", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            modifier = Modifier.testTag("nav_contact")
        )
    }
}
