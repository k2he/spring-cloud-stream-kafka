export interface ApplicationProcessStatus {
    applicationStatus: ApplicationStatus[];
    processDurationInSeconds: number;
    processFinished: boolean;
}


export interface ApplicationStatus {
    applicationNumber: string;
    bureaStatus: string;
    ajdcStatus: string;
    tsysStatus: string;
    lastUpdatedDate: Date;
    createdDate: Date;
    processTimeInSeconds: number;
}

export interface ApplicationStartResponse {
    message: string;
}

export interface ProcessingSummary {
    COMPLETED: number;
    FAILED: number;
}

