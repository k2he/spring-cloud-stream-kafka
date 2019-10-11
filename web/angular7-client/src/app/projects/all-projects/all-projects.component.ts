import { Component, OnInit, ViewChild, AfterViewChecked } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';
import { MatDialog, MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';

import { ProjectService } from '../../api/project.service';
import { ProjectInfo } from "../../resources/project";
import { Observable } from 'rxjs';
import { CustomCurrencyPipe } from '../../shared/pipes/custom-currency.pipe';
import { DialogComponent } from '../../dialog/dialog.component';
import { NotificationService } from '../../services/notification.service';

@Component({
    selector: 'app-all-projects',
    templateUrl: './all-projects.component.html',
    styleUrls: ['./all-projects.component.scss']
})
export class AllProjectsComponent implements OnInit, AfterViewChecked {

    displayedColumns = ['name', 'description', 'due', 'skills', 'estimatedCost', 'status', 'actions'];
    dataSource;

    @ViewChild(MatSort) sort: MatSort;

    constructor(private projectService: ProjectService,
        private translate: TranslateService,
        private notifyService: NotificationService,
        public dialog: MatDialog) {
    }

    ngOnInit() {
        this.fetchData();
    }

    fetchData() {
        this.projectService.getAllProjects().subscribe(
            result => this.dataSource = new MatTableDataSource(result)
        );
    }
    /**
     * Set the sort after the view init since this component will
     * be able to query its view for the initialized sort.
     */

    ngAfterViewChecked() {
        if (this.dataSource) {
            this.dataSource.sort = this.sort;
        }
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

    onDelete(projectId: string) {
        this.openDialog(projectId);
    }

    openDialog(projectId: string): void {
        let dialogRef = this.dialog.open(DialogComponent, {
            data: { title: this.translate.instant('projects-page.all-projects-page.delete-title'), content: this.translate.instant('projects-page.all-projects-page.delete-content') }
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed. Result is ' + result);
            if (result == 'yes') {
                this.processDelete(projectId);
            }
        });
    }

    processDelete(projectId: string) {
        this.projectService.deleteProjectInfoById(projectId).subscribe(
            result => {
                const message = this.translate.instant('projects-page.delete-success-message');
                this.notifyService.showSuccess(message);
                this.fetchData();
            });
    }
}
