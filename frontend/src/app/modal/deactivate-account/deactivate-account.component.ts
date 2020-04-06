import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NgxSpinnerService} from 'ngx-spinner';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../../service/account.service';

@Component({
  templateUrl: './deactivate-account.component.html',
  styleUrls: ['./deactivate-account.component.css']
})
export class DeactivateAccountComponent implements OnInit {

  form: FormGroup;

  constructor(private activeModal: NgbActiveModal,
              private spinner: NgxSpinnerService,
              private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      password: new FormControl(null, {validators: Validators.required, updateOn: 'submit'})
    });
  }

  get password() {
    return this.form.controls.password;
  }

  onDeactivate() {
    if (this.form.valid) {
      this.spinner.show();
      this.accountService.deactivateAccount(this.password.value)
        .subscribe(() => {
          this.spinner.hide();
          this.activeModal.close(true);
        }, () => {
          this.spinner.hide();
          this.password.setErrors({incorrectPassword: true});
        });
    } else {
      this.password.markAsTouched();
    }
  }
}
