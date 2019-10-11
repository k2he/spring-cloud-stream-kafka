export interface ProjectInfo {
    projectId: number;
    projectName: string;
    projectSummary: string;
    dueDate: Date;
    requiredSkills: string;
    estimatedCost: number;
    statusId: number;
    projectStatus: ProjectStatus;
    createdOn: Date;
    createdBy: string;
    updatedOn: Date;
    lastupdatedBy: string;
}


export interface ProjectStatus {
    id: number;
    description: string;
}

