import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferirBeneficioComponent } from './transferir-beneficio.component';

describe('TransferirBeneficioComponent', () => {
  let component: TransferirBeneficioComponent;
  let fixture: ComponentFixture<TransferirBeneficioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferirBeneficioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferirBeneficioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
