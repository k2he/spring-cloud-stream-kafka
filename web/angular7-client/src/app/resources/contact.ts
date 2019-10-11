export interface ContactInfo {
    id: number;
    name: string;
    email: string;
    phoneNumber: string;
    subject: string;
    message: string;
    createdOn: Date;
    updatedOn: Date;
    isResolved: boolean;
    resovedBy: string;
}