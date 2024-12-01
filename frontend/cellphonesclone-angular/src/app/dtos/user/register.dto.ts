import{
    IsString,
    IsNotEmpty,
    IsPhoneNumber,
    IsDate
} from 'class-validator';

export class RegisterDTO {
    @IsString()
    fullname: string;

    @IsPhoneNumber()
    phone_number: string;

    @IsString()
    @IsNotEmpty()
    user_address: string;

    @IsString()
    @IsNotEmpty()
    user_password: string;
    
    @IsString()
    @IsNotEmpty()
    retype_password: string;

    @IsDate()
    date_of_birth: Date;
    facebook_account_id: number = 0;
    google_account_id: number = 0;
    role_id: number = 1;

    constructor(data: any) {
        this.fullname = data.fullName;
        this.phone_number = data.phoneNumber;
        this.user_address = data.address;
        this.user_password = data.password;
        this.retype_password = data.retypePassword;
        this.date_of_birth = data.dateOfBirth;
        this.facebook_account_id = data.facebookAccountId;
        this.google_account_id = data.googleAccountId;
        this.role_id = data.roleId;
    }
}
