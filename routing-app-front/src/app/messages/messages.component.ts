import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { Message, MessageStatus } from '../models/message.model';
import { MessageService } from '../services/message.service';
import { MessageDialogComponent } from './message-dialog/message-dialog.component';
import {MatPaginatorModule} from "@angular/material/paginator";

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatCardModule,
    MatPaginatorModule
  ],
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {
  messages: Message[] = [];
  displayedColumns: string[] = ['id', 'content', 'source', 'queueName', 'receivedAt', 'status', 'actions'];
// Pagination variables
  totalElements: number = 0;
  pageSize: number = 5; // Default page size
  currentPage: number = 0;

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.messageService.getMessages(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.messages = response.content; // Load messages
        this.totalElements = response.totalElements; // Total number of elements
      },
      error: (error) => {
        console.error('Error loading messages:', error);
      }
    });
  }
  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadMessages();
  }

  viewMessageDetails(message: Message): void {
    this.dialog.open(MessageDialogComponent, {
      width: '500px',
      data: message
    });
  }

  getStatusColor(status: MessageStatus): string {
    switch (status) {
      case MessageStatus.NEW:
        return 'blue';
      case MessageStatus.PROCESSED:
        return 'green';
      case MessageStatus.ERROR:
        return 'red';
      default:
        return 'black';
    }
  }
}
