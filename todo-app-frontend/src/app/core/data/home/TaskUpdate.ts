export interface TaskUpdate {
    taskId: number;

    description: string;

    title: string;

    dueDate: string;

    priority: number;

    collaboratorUsernames: string[];
}
