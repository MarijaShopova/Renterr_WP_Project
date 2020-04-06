import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Account} from '../../model/account';
import {NgxSpinnerService} from 'ngx-spinner';
import {AccountService} from '../../service/account.service';

@Component({
  templateUrl: './edit-account.component.html',
  styleUrls: ['./edit-account.component.css']
})
export class EditAccountComponent implements OnInit {

  @Input() account: Account;

  editForm: FormGroup;

  constructor(private accountService: AccountService,
              private activeModal: NgbActiveModal,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    this.editForm = this.accountService.initializeEditForm(this.account.email);
    this.editForm.patchValue(this.account);
  }

  get form(): { [p: string]: AbstractControl } {
    return this.editForm.controls;
  }

  onSubmit() {
    if (this.editForm.valid) {
      this.spinner.show();
      this.accountService.updateAccount(this.editForm.value)
        .subscribe(newAccount => {
          this.spinner.hide();
          this.activeModal.close(newAccount);
        }, err => {
          this.spinner.hide();
          this.activeModal.dismiss(err.status);
        });
    } else {
      Object.keys(this.editForm.controls).forEach(control => this.editForm.get(control).markAsTouched());
    }
  }
}
