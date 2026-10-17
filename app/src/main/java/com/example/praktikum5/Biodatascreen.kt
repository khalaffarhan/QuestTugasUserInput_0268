package com.example.praktikum5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen(modifier: Modifier = Modifier) {
    // ---------- State ----------
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val experienceOptions = stringArrayResource(R.array.experience_options)
    val skillOptions = stringArrayResource(R.array.skill_options)
    val skillChecked = remember { mutableStateListOf<Boolean>().apply { repeat(skillOptions.size) { add(false) } } }

    val isFormValid = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            birthDate.isNotBlank() &&
            experience.isNotBlank() &&
            skillChecked.any { it }

    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.title_purple),
        unfocusedBorderColor = colorResource(R.color.field_outline),
        focusedTextColor = colorResource(R.color.text_primary),
        unfocusedTextColor = colorResource(R.color.text_primary),
        focusedLabelColor = colorResource(R.color.title_purple),
        unfocusedLabelColor = colorResource(R.color.field_hint),
        cursorColor = colorResource(R.color.title_purple)
    )
    val datePattern = stringResource(R.string.date_pattern)
    val bodySize: TextUnit = dimensionResource(R.dimen.body_size).value.sp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.screen_background))
    ) {
        // Gambar latar belakang
        Image(
            painter = painterResource(R.drawable.bg_foto),
            contentDescription = stringResource(R.string.cd_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.image_height))
        )

        // Indikator titik di atas gambar
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(dimensionResource(R.dimen.dot_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dot_spacing))
        ) {
            repeat(integerResource(R.integer.pager_dot_count)) { index ->
                val color = if (index == integerResource(R.integer.pager_dot_active_index))
                    colorResource(R.color.dot_active) else colorResource(R.color.dot_inactive)
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.dot_size))
                        .background(color, CircleShape)
                )
            }
        }

        // ===== BOX MENGAPUNG DI ATAS SCAFFOLD =====
        val cardShape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.card_corner_large),
            topEnd = dimensionResource(R.dimen.card_corner_large),
            bottomStart = dimensionResource(R.dimen.card_corner_small),
            bottomEnd = dimensionResource(R.dimen.card_corner_small)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    top = dimensionResource(R.dimen.card_margin_top),
                    bottom = dimensionResource(R.dimen.card_margin_bottom)
                )
                .fillMaxWidth()
                .shadow(
                    elevation = dimensionResource(R.dimen.card_elevation),
                    shape = cardShape,
                    spotColor = colorResource(R.color.card_shadow)
                )
                .background(colorResource(R.color.card_background), cardShape)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = dimensionResource(R.dimen.card_padding_horizontal),
                        vertical = dimensionResource(R.dimen.card_padding_vertical)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Judul
                Text(
                    text = stringResource(R.string.form_title),
                    color = colorResource(R.color.title_purple),
                    fontSize = dimensionResource(R.dimen.title_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.space_tiny)))
                Text(
                    text = stringResource(R.string.form_subtitle),
                    color = colorResource(R.color.subtitle_gray),
                    fontSize = dimensionResource(R.dimen.subtitle_size).value.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.space_large)))

                // Nama Depan & Nama Belakang
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small))
                ) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = { Text(stringResource(R.string.hint_first_name), fontSize = bodySize) },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = { Text(stringResource(R.string.hint_last_name), fontSize = bodySize) },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(dimensionResource(R.dimen.space_medium)))

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(stringResource(R.string.hint_email), fontSize = bodySize) },
                    singleLine = true,
                    shape = fieldShape,
                    colors = fieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.space_medium)))

                // Tgl Lahir & Experience
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_small))
                ) {
                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(stringResource(R.string.hint_birth_date), fontSize = bodySize) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_calendar),
                                    contentDescription = stringResource(R.string.cd_pick_date),
                                    tint = colorResource(R.color.text_primary)
                                )
                            }
                        },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        modifier = Modifier.weight(1f)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = experience,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text(stringResource(R.string.hint_experience), fontSize = bodySize) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors,
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            experienceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        experience = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(dimensionResource(R.dimen.space_medium)))

                // Pilih Skill
                Text(
                    text = stringResource(R.string.label_skill),
                    color = colorResource(R.color.text_primary),
                    fontSize = dimensionResource(R.dimen.label_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    skillOptions.forEachIndexed { index, skill ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = skillChecked[index],
                                onCheckedChange = { skillChecked[index] = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = colorResource(R.color.checkbox_checked),
                                    uncheckedColor = colorResource(R.color.checkbox_unchecked),
                                    checkmarkColor = colorResource(R.color.white)
                                )
                            )
                            Text(
                                text = skill,
                                color = colorResource(R.color.text_primary),
                                fontSize = dimensionResource(R.dimen.checkbox_text_size).value.sp
                            )
                        }
                    }
                }
                Spacer(Modifier.height(dimensionResource(R.dimen.space_medium)))

                // Tombol Proceed
                Button(
                    onClick = { /* aksi proceed */ },
                    enabled = isFormValid,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.button_enabled),
                        contentColor = colorResource(R.color.button_text_enabled),
                        disabledContainerColor = colorResource(R.color.button_disabled),
                        disabledContentColor = colorResource(R.color.button_text_disabled)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_height))
                ) {
                    Text(
                        text = stringResource(R.string.button_proceed),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

