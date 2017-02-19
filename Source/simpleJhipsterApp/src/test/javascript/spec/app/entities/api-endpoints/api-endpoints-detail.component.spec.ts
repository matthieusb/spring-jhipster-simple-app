import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ApiEndpointsDetailComponent } from '../../../../../../main/webapp/app/entities/api-endpoints/api-endpoints-detail.component';
import { ApiEndpointsService } from '../../../../../../main/webapp/app/entities/api-endpoints/api-endpoints.service';
import { ApiEndpoints } from '../../../../../../main/webapp/app/entities/api-endpoints/api-endpoints.model';

describe('Component Tests', () => {

    describe('ApiEndpoints Management Detail Component', () => {
        let comp: ApiEndpointsDetailComponent;
        let fixture: ComponentFixture<ApiEndpointsDetailComponent>;
        let service: ApiEndpointsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ApiEndpointsDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    ApiEndpointsService
                ]
            }).overrideComponent(ApiEndpointsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApiEndpointsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApiEndpointsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ApiEndpoints('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.apiEndpoints).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
