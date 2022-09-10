package com.example.wifood.domain.usecase

data class WifoodUseCases(
    val ValidatePhone: ValidatePhone,
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val validateNickname: ValidateNickname,
    val validateBirthday: ValidateBirthday,
    val validateTerms: ValidateTerms,
    val GetUserAllData: GetUserAllData,
    val InsertUser: InsertUser,
    val DeletePlace: DeletePlace,
    val GetPlaceImageUris: GetPlaceImageUris,
    val DeleteGroup: DeleteGroup,
    val InsertGroup: InsertGroup,
    val UpdateGroup: UpdateGroup,
    val GetGroups: GetGroups,
    val InsertPlace: InsertPlace,
    val UpdatePlace: UpdatePlace,
    val InsertPlaceImages: InsertPlaceImages,
    val GetUserInfo: GetUserInfo,
    val GetTMapSearchPlaceResult: GetTMapSearchPlaceResult,
    val GetTMapSearchAddressResult: GetTMapSearchAddressResult,
    val GetPlaceImageUri: GetPlaceImageUri,
    val RequestCertNumber: RequestCertNumber,
    val GetTMapSearchDetailAddressResult: GetTMapSearchDetailAddressResult,
    val GetTMapReverseGeocoding: GetTMapReverseGeocoding,
    val CheckUser: CheckUser
)
