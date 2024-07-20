// export class Profile {
//     constructor() {
//         this.id = id;
//         this.firstName = firstName;
//         this.addition = addition;
//         this.lastName = lastName;
//         this.bio = bio;
//         this.email = email;
//         this.phoneNumber = phoneNumber;
//         this.country = country;
//         this.residence = residence;
//         this.address = address;
//         this.houseNumber = houseNumber;
//         this.postalCode = postalCode;
//     }
//
//     static createProfile(pId = 0, pFirstName, pAddition, pLastName, pBio, pEmail, pPhone, pCountry, pResidence, pAddress, pHouseNumber, pPostalCode) {
//         return new Profile(pId, pFirstName, pAddition, pLastName, pBio, pEmail, pPhone, pCountry, pResidence, pAddress, pHouseNumber, pPostalCode);
//     }
//
//     static copyConstructor(profile) {
//         if (profile == null) {
//             return null;
//         }
//         return Object.assign(new Profile(), profile);
//     }
// }