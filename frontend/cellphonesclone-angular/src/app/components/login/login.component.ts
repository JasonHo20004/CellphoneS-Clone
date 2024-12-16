import { Component, ViewChild } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { LoginDTO } from '../../dtos/user/login.dto';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { HttpClientModule } from '@angular/common/http';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { Role } from '../../models/role';
import { RoleService } from '../../services/role.service';
import { UserResponse } from '../../responses/user/user.response';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FooterComponent, FormsModule, HttpClientModule, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;

  phoneNumber: string ='091236451';
  password: string = '123456';

  roles: Role[] = []; // Mảng roles
  rememberMe: boolean = true;
  selectedRole: Role | undefined; // Biến để lưu giá trị được chọn từ dropdown
  userResponse?: UserResponse

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  constructor(
    private router: Router, 
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) { }


  ngOnInit() {
    // Gọi API lấy danh sách roles và lưu vào biến roles
    debugger
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => { // Sử dụng kiểu Role[]
        debugger
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      error: (error: any) => {
        debugger
        console.error('Error getting roles:', error);
      }
    });
  }
  // login() {
  //   console.log('Login method called');
  //   const message = `phone: ${this.phoneNumber}` + `user_password: ${this.password}`

  //   const loginDTO: LoginDTO = {
      
  //     "phone_number": this.phoneNumber,
  //     "password": this.password,
  //   };
  //   this.userService.login(loginDTO).subscribe({
  //     next: (response: any) => {
  //       if (response && (response.status === 200 || response.status === 201)) {
  //         //this.router.navigate(['/login']);
  //       }
  //     },
  //     error: (error: any) => {
  //       alert(`Cannot login, error: ${error.error}`);
  //     }
  //   });
  // }

  // login() {
  //   const message = `phone: ${this.phoneNumber}` +
  //     `password: ${this.password}`;
  //   //alert(message);
  //   debugger

  //   const loginDTO: LoginDTO = {
  //     phone_number: this.phoneNumber,
  //     password: this.password,
  //     role_id: this.selectedRole?.id ?? 1
  //   };
  //   debugger
  //   this.userService.login(loginDTO).subscribe({
  //     next: (response: LoginResponse) => {
  //       debugger;
  //       const { token } = response;
  //       if (this.rememberMe) {
  //         this.tokenService.setToken(token);
  //         debugger
  //       }
  //       this.userService.getUserDetail(token).subscribe({
  //         next: (response:any) => {
  //           debugger
  //           this.userResponse ={
  //             ...response,
  //             date_of_birth: new Date(response.date_of_birth)
  //           };
  //           this.userService.saveUserResponseToLocalStorage(this.userResponse)
  //           this.router.navigate(['/']);

  //         }
  //       })
  //     },
  //     complete: () => {
  //       debugger;
  //     },
  //     error: (error: any) => {
  //       debugger;
  //       alert(error.error.message);
  //     }
  //   });
  // }

  login() {
    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1
    };
  
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        const { token } = response;
        if (this.rememberMe) {
          this.tokenService.setToken(token);
        }
  
        // Fetch user details
        this.userService.getUserDetail(token).subscribe({
          next: (userDetailResponse: UserResponse) => {
            this.userResponse = {
              ...userDetailResponse,
              date_of_birth: new Date(userDetailResponse.date_of_birth) 
            };
            this.userService.saveUserResponseToLocalStorage(this.userResponse);
            this.router.navigate(['/']);
          },
          error: (error) => {
            console.error('Error fetching user details:', error);
            // Handle error (e.g., show error message)
          }
        });
      },
      error: (error: any) => {
        alert(error.error.message);
      }
    });
  }
}
