import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DepartmentsService {

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get(`/api/departments/all`).pipe(map(res => res));
  }

  getById(id) {
    return this.http.get(`/api/departments/${id}`).pipe(map(res => res));
  }

}
