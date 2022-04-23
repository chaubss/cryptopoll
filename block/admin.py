from django.contrib import admin
from .models import Block
from vote.models import Vote

class BlockVotesInline(admin.TabularInline):
    model = Vote
    extra = 1

# Register your models here.
class BlockAdmin(admin.ModelAdmin):
    list_display = ('height', 'hash', 'previous_hash', 'timestamp', 'status', 'nounce')
    list_filter = ('height', 'hash', 'previous_hash', 'timestamp', 'status', 'nounce')
    search_fields = ('height', 'hash', 'previous_hash', 'timestamp', 'status', 'nounce')
    inlines = (BlockVotesInline,)

admin.site.register(Block, BlockAdmin)