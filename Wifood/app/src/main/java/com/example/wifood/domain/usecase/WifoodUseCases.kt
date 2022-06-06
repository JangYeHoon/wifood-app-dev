package com.example.wifood.domain.usecase

data class WifoodUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val validateTerms: ValidateTerms,
    val GetUser: GetUser,
    val DeletePlace: DeletePlace,
    val GetPlaceImageUris: GetPlaceImageUris,
    val DeleteGroup: DeleteGroup
)
