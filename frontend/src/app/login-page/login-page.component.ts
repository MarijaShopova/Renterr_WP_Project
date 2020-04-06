import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {AuthenticationService} from '../service/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxSpinnerService} from 'ngx-spinner';

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  passwordInputType = false;
  showEye = false;
  errorMessage: string = null;
  signInForm: FormGroup;
  showAlert: boolean;
  @ViewChild('username', {static: true}) username: ElementRef;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private spinner: NgxSpinnerService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => this.showAlert = params.showAlert);
    this.signInForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    });
    this.signInForm.valueChanges.subscribe(() => this.errorMessage = null);
  }

  onSubmit() {
    this.spinner.show();
    this.authenticationService.logIn(this.signInForm.value)
      .subscribe(() => {
        this.spinner.hide();
        this.router.navigate(['/']);
      }, err => {
        this.errorMessage = err;
        this.spinner.hide();
      });
  }

  onShowPassword() {
    this.passwordInputType = !this.passwordInputType;
    this.showEye = !this.showEye;
  }
}
