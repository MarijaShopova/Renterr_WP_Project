import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {AuthenticationService} from '../../service/authentication.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NgxSpinnerService} from 'ngx-spinner';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  passwordInputType = false;
  showEye = false;
  errorMessage: string = null;
  loginForm: FormGroup;

  constructor(private authenticationService: AuthenticationService,
              public activeModal: NgbActiveModal,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    });
    this.loginForm.valueChanges.subscribe(() => this.errorMessage = null);
  }

  onSubmit() {
    this.spinner.show();
    this.authenticationService.logIn(this.loginForm.value)
      .subscribe(() => {
          this.spinner.hide();
          this.activeModal.close(true);
        },
        errorMessage => {
          this.errorMessage = errorMessage;
          this.spinner.hide();
        });
  }

  onShowPassword() {
    this.passwordInputType = !this.passwordInputType;
    this.showEye = !this.showEye;
  }
}
