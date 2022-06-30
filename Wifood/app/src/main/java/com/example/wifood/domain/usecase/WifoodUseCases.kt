package com.example.wifood.domain.usecase

data class WifoodUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val validateNickname: ValidateNickname,
    val validateBirthday: ValidateBirthday,
    val validateTerms: ValidateTerms,
    val validateGroupName: ValidateGroupName,
    val GetUser: GetUser,
    val InsertUser: InsertUser,
    val DeletePlace: DeletePlace,
    val GetPlaceImageUris: GetPlaceImageUris,
    val DeleteGroup: DeleteGroup,
    val InsertGroup: InsertGroup,
    val UpdateGroup: UpdateGroup,
    val GetGroups: GetGroups,
    val InsertPlace: InsertPlace,
    val InsertPlaceImages: InsertPlaceImages
)
