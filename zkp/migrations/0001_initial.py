# Generated by Django 4.0.4 on 2022-04-25 10:55

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ZKPObject',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('h', models.IntegerField()),
                ('y', models.IntegerField()),
                ('b', models.IntegerField()),
            ],
        ),
    ]