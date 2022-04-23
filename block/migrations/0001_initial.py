# Generated by Django 4.0.4 on 2022-04-23 13:27

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Block',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
                ('height', models.IntegerField(default=0)),
                ('hash', models.CharField(max_length=255)),
                ('previous_hash', models.CharField(max_length=255)),
                ('status', models.IntegerField(default=0)),
                ('nounce', models.IntegerField(null=True)),
            ],
        ),
    ]