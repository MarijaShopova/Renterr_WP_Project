import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-grid-options',
  templateUrl: './grid-options.component.html',
  styleUrls: ['./grid-options.component.css']
})
export class GridOptionsComponent {

  @Input() sort: string;
  @Input() currency: string;
  @Input() total: number;

  @Output() optionsChanged = new EventEmitter();

  onOptionsChange(resetPaging: boolean) {
    const options: { [k: string]: any } = {sort: this.sort, currency: this.currency};
    if (resetPaging) {
      options.page = 0;
    }
    this.optionsChanged.emit(options);
  }
}
