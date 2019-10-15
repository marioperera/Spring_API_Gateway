import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatInputModule, MatButtonModule, MatSelectModule, MatIconModule, MatCardModule, MatRadioModule} from '@angular/material';
import { FormsModule,  ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterApiComponent } from './register-api/register-api.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { PublishNewApiComponent } from './components/publish-new-api/publish-new-api.component';
import {  BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import { SearchapiComponent } from './components/searchapi/searchapi.component';
import { CreatenewapiComponent } from './components/createnewapi/createnewapi.component'; 
import {MatCheckboxModule} from '@angular/material/checkbox'; 
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { from } from 'rxjs';

@NgModule({
  declarations: [
    AppComponent,
    RegisterApiComponent,
    PublishNewApiComponent,
    SearchapiComponent,
    CreatenewapiComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NoopAnimationsModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatCardModule,
    MatRadioModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatCardModule,
    HttpClientModule,
    MatSelectModule
  ],
  providers: [HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
