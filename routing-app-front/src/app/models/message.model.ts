export interface Message {
  id?: number;
  content: string;
  source: string;
  queueName: string;
  receivedAt: Date;
  processedAt?: Date;
  status: MessageStatus;
}

export enum MessageStatus {
  NEW = 'NEW',
  PROCESSING = 'PROCESSING',
  PROCESSED = 'PROCESSED',
  ERROR = 'ERROR'
}
