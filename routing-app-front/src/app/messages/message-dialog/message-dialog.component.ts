import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Message } from '../../models/message.model';

@Component({
  selector: 'app-message-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>Message Details</h2>
    <mat-dialog-content>
      <div class="message-details">
        <p><strong>ID:</strong> {{message.id}}</p>
        <p><strong>Content:</strong> {{message.content}}</p>
        <p><strong>Source:</strong> {{message.source}}</p>
        <p><strong>Status:</strong> <span [class]="'status-' + message.status.toLowerCase()">{{message.status}}</span></p>
        <p><strong>Received At:</strong> {{message.receivedAt | date:'medium'}}</p>
        <p *ngIf="message.processedAt"><strong>Processed At:</strong> {{message.processedAt | date:'medium'}}</p>
        <p><strong>Queue Name:</strong> {{message.queueName}}</p>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Close</button>
    </mat-dialog-actions>
  `,
  styles: [`
    .message-details {
      padding: 20px;

      p {
        margin-bottom: 10px;
      }
    }

    .status-new { color: blue; }
    .status-processed { color: green; }
    .status-error { color: red; }
  `]
})
export class MessageDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<MessageDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public message: Message
  ) {}
}
