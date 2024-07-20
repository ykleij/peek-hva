export class UserProfile {
    constructor(id,
                firstName,
                addition,
                bio,
                address,
                houseNumber,
                postalCode,
                phoneNumber,
                country,
                residence,
                kvkNumber,
                linkedInUrl,
                imageData
                ) {
        this.id = id;
        this.firstName = firstName;
        this.addition = addition;
        this.bio = bio;
        this.address = address;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.residence = residence;
        this.kvkNumber = kvkNumber;
        this.linkedInUrl = linkedInUrl;
        this.imageData = imageData;
    }

    static create(pId = 0, pFirstName, pAddition, pBio, pAddress, pHouseNumber, pPostalCode, pPhoneNumber, pCountry, pResidence, pKvkNumber, plinkedInUrl, pImageData, pEmail, pRole) {
        return new UserProfile(
            pId,
            pFirstName,
            pAddition,
            pBio,
            pAddress,
            pHouseNumber,
            pPostalCode,
            pPhoneNumber,
            pCountry,
            pResidence,
            pKvkNumber,
            plinkedInUrl,
            pImageData,
            pEmail,
            pRole
        );
    }


    static copyConstructor(userProfile) {
        if (userProfile == null) {
            return null;
        }
        return Object.assign(new UserProfile(), userProfile);
    }
}
