package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Department
import com.example.data.models.Doctor
import com.example.ui.components.DoctorCard

@Composable
fun DoctorsScreen(
    doctors: List<Doctor>,
    departments: List<Department>,
    searchQuery: String,
    selectedDeptFilter: String,
    onSearchQueryChange: (String) -> Unit,
    onDeptFilterChange: (String) -> Unit,
    onBookDoctor: (Doctor) -> Unit,
    onDoctorDetail: (Doctor) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredDoctors = doctors.filter { doc ->
        val matchesDept = selectedDeptFilter.equals("all", ignoreCase = true) ||
                doc.departmentId.equals(selectedDeptFilter, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                doc.name.contains(searchQuery, ignoreCase = true) ||
                doc.degrees.contains(searchQuery, ignoreCase = true) ||
                doc.departmentName.contains(searchQuery, ignoreCase = true) ||
                doc.title.contains(searchQuery, ignoreCase = true)
        matchesDept && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("doctors_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search doctor by name, qualification, or specialty...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Search")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("doctor_search_input"),
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Department Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedDeptFilter.equals("all", ignoreCase = true),
                    onClick = { onDeptFilterChange("all") },
                    label = { Text("All Departments") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("chip_dept_all")
                )
            }

            items(departments) { dept ->
                FilterChip(
                    selected = selectedDeptFilter.equals(dept.id, ignoreCase = true),
                    onClick = { onDeptFilterChange(dept.id) },
                    label = { Text(dept.name.split("&").first().trim()) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("chip_dept_${dept.id}")
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Doctors List
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Specialists on Duty (${filteredDoctors.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            if (filteredDoctors.isEmpty()) {
                item {
                    Text(
                        text = "No specialists found matching your search.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            } else {
                items(filteredDoctors) { doc ->
                    DoctorCard(
                        doctor = doc,
                        onBookClick = { onBookDoctor(doc) },
                        onViewDetail = { onDoctorDetail(doc) }
                    )
                }
            }
        }
    }
}
