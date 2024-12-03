import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Partner} from "../models/partner.model";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  private apiUrl = `${environment.apiPartenaireService.baseUrl}${environment.apiPartenaireService.endpoints}`;

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Partner[]>(this.apiUrl);
  }

  create(partner: Partner) {
    return this.http.post<Partner>(this.apiUrl, partner);
  }

  update(id: number, partner: Partner) {
    return this.http.put<Partner>(`${this.apiUrl}/${id}`, partner);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
