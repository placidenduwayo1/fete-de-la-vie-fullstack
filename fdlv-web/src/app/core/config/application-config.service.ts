import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {
  private endpointApi = '';
  private endpointManagement = '';
  private endpoint = '';
  private endpointPrefix = '';

 
  constructor() {
    this.setEndpoints(environment.apiUrl);
  }

  setEndpointPrefix(endpointPrefix: string): void {
    this.endpointPrefix = endpointPrefix;
  }

  setEndpoints(url: string): void {
    this.endpointApi = `${url}/api`;
    this.endpointManagement = `${url}/management`;
    this.endpoint = url;
  }

  getEndpoint(): string {
    return this.endpoint;
  }

  getEndpointApiFor(route: string): string {
    return `${this.endpointApi}${route}`;
  }

  getEndpointManagementFor(route: string): string {
    return `${this.endpointManagement}${route}`;
  }
  getEndpointFor(api: string, microservice?: string): string {
    if (microservice) {
      return `${this.endpointPrefix}services/${microservice}/${api}`;
    }
    return `${this.endpointPrefix}${api}`;
  }
}
