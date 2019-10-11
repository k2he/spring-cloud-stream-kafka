import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

const DIALOG_YES = "yes";

@Component({
    selector: 'app-dialog',
    templateUrl: './dialog.component.html',
    styleUrls: ['./dialog.component.scss']
})
export class DialogComponent {

    constructor(public dialogRef: MatDialogRef<DialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
    }

    onConfirm(): void {
        this.dialogRef.close('yes');
    }

    onCancell(): void {
        this.dialogRef.close();
    }

}
