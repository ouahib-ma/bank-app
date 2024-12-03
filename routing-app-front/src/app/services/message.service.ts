import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { Message } from '../models/message.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = `${environment.apiMessageService.baseUrl}${environment.apiMessageService.endpoints}`;

  constructor(private http: HttpClient) {}

  getMessages(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  getMessage(id: number): Observable<Message> {
    return this.http.get<Message>(`${this.apiUrl}/${id}`);
  }

  getMessagesByStatus(status: string): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/status/${status}`);
  }
}
