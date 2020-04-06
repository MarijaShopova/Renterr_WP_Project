import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {NgxSpinnerService} from 'ngx-spinner';
import {AccountService} from '../service/account.service';

@Component({
  templateUrl: './sign-up-page.component.html',
  styleUrls: ['./sign-up-page.component.css']
})
export class SignUpPageComponent implements OnInit {

  imageFile: File;
  fileName = 'Choose a profile picture';
  signUpForm: FormGroup;
  passwordInputType = false;
  showEye = false;

  @ViewChild('username', {static: true}) username: ElementRef;

  constructor(private accountService: AccountService,
              private router: Router,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.signUpForm = this.accountService.initializeSignUpForm();
    this.username.nativeElement.focus();
  }

  get form() {
    return this.signUpForm.controls;
  }

  onShowPassword() {
    this.passwordInputType = !this.passwordInputType;
    this.showEye = !this.showEye;
  }

  onFileChanged(event) {
    if (event.target.files.length) {
      this.imageFile = event.target.files[0] as File;
      this.fileName = this.imageFile.name;
    }
  }

  onSubmit() {
    if (this.signUpForm.valid) {
      this.spinner.show();
      const formData = new FormData();
      formData.append('account', JSON.stringify(this.signUpForm.value));
      if (this.imageFile) {
        formData.append('image', this.imageFile);
      }
      this.accountService.registerUser(formData)
        .subscribe(() => {
          this.spinner.hide();
          this.router.navigate(['/login'], {queryParams: {showAlert: true}});
        }, this.onError);
    } else {
      Object.keys(this.signUpForm.controls).forEach(control => this.signUpForm.get(control).markAsTouched());
    }
  }

  onError = err => {
    this.spinner.hide();
    this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
  };
}
