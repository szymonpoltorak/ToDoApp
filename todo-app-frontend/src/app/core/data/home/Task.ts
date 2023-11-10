import { Collaborator } from "@core/data/home/Collaborator";

export interface Task {
    taskId: number;

    description: string;

    title: string;

    dueDate: string;

    priority: number;

    isCompleted: boolean;

    collaborators: Collaborator[];
}
