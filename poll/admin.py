from django.contrib import admin
from .models import Poll
from choice.models import Choice

# Register your models here.

class PollChoicesInline(admin.TabularInline):
  model = Choice
  extra = 1

class PollAdmin(admin.ModelAdmin):
    list_display = ('name', 'description', 'start_date', 'end_date')
    list_filter = ('name', 'description', 'start_date', 'end_date')
    search_fields = ('name', 'description', 'start_date', 'end_date')
    inlines = (PollChoicesInline,)

admin.site.register(Poll, PollAdmin)