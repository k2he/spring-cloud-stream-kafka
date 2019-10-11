import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CustomCurrencyPipe } from '../../shared/pipes/custom-currency.pipe';
import { ProjectInfo } from "../../resources/project";
import { ProjectService } from '../../api/project.service';
import { UtilService } from '../../services/util.service';
import { NewProjectCountService } from '../../services/new-project-count.service';
import { NotificationService } from '../../services/notification.service';

@Component({
    selector: 'app-new-project',
    templateUrl: './new-project.component.html',
    styleUrls: ['./new-project.component.scss']
})
export class NewProjectComponent implements OnInit {
    FIELD_MIN: number = 3;

    currencyPipe: CustomCurrencyPipe;
    projectInfo: ProjectInfo = <ProjectInfo>{};
    projectForm: FormGroup;
    title: string;
    isOnEditMode: boolean = false;

    constructor(private fb: FormBuilder,
        private utilService: UtilService,
        private projectService: ProjectService,
        private service: NewProjectCountService,
        private notifyService: NotificationService,
        private translate: TranslateService,
        private route: ActivatedRoute) {
        this.projectForm = fb.group({
            'name': ['', [Validators.required, Validators.minLength(this.FIELD_MIN), Validators.maxLength(100)]],
            'skills': ['', null],
            'dueDate': ['', Validators.required],
            'cost': ['', [Validators.required, Validators.max(99999999999)]],
            'description': ['', [Validators.required, Validators.minLength(this.FIELD_MIN), Validators.maxLength(500)]]
        });
    }

    ngOnInit() {
        this.currencyPipe = new CustomCurrencyPipe();
        const productID = this.route.snapshot.params['id'];
        if (productID) {
            this.loadProjectInfo(productID);
        }
        this.isOnEditMode = productID ? true : false;
    }

    loadProjectInfo(productID: number) {
        this.projectService.getProjectInfoById(productID)
            .subscribe(info => this.projectInfo = info);
    }

    onSubmit() {
        this.utilService.deepTrim(this.projectInfo);
        if (this.isOnEditMode) {
            this.updateProject();
        } else {
            this.createProject();
        }
    }
    
    createProject() {
        this.parseProjectInfo(this.projectInfo);
        this.projectService.createProjectInfo(this.projectInfo)
            .subscribe(() => {
                this.service.newEvent('add');
                const message = this.translate.instant('projects-page.create-success-message')
                this.notifyService.showSuccess(message);
            });
    }

    updateProject() {
        this.parseProjectInfo(this.projectInfo);
        this.projectService.updateProjectInfo(this.projectInfo)
            .subscribe(() => {
                const message = this.translate.instant('projects-page.update-success-message')
                this.notifyService.showSuccess(message);
            });
    }
    
    // Strip off convert $ to number.
    parseProjectInfo(project: ProjectInfo) {
        let cost = String(project.estimatedCost);
        project.estimatedCost = +this.currencyPipe.parse(cost);
    }
}
