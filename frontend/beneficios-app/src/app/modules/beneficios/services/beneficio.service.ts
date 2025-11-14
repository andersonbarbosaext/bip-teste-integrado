import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio';
import { TransferRequest } from '../models/transfer-request';

@Injectable({ providedIn: 'root' })
export class BeneficioService {

  private api = 'http://localhost:8081/api/v1/beneficios';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.api);
  }

  findById(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/${id}`);
  }

  create(b: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.api, b);
  }

  update(id: number, b: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.api}/${id}`, b);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  transfer(req: TransferRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/transfer`, req);
  }

}