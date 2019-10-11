import { Component, OnInit } from '@angular/core';

import { SpinnerService } from '../../services/spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent implements OnInit {

  isLoading$;

  constructor(private spinnerServie: SpinnerService) { }

  ngOnInit() {
    this.isLoading$ = this.spinnerServie.events$;
  }

}
