export interface ApplicationProcessStatus {
    applicationStatus: ApplicationStatus[];
    processDurationInSeconds: number;
    processFinished: boolean;
}


export interface ApplicationStatus {
    applicationNumber: string;
    bureaStatus: string;
    ajdcStatus: string;
    lastUpdatedDate: Date;
    createdDate: Date;
}

export interface ApplicationStartResponse {
    message: string;
}

