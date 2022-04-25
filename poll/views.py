from django.db import IntegrityError
from django.utils.timezone import localtime, now
from rest_framework.decorators import permission_classes, api_view
from rest_framework.permissions import IsAuthenticated
from authentication.role_authentication import IsRoleAdmin, IsRoleUser, IsRoleUserOrAdmin
from rest_framework.response import Response
from .models import Poll
from choice.models import Choice
from vote.models import Vote
from django.db import transaction
from blockchain.models import Blockchain
from .serializers import PollSerializer

# Create your views here.
@api_view(['POST'])
@permission_classes((IsAuthenticated, IsRoleUserOrAdmin))
def create_poll(request):
    try:
        with transaction.atomic():
            poll = Poll.objects.create(
                name=request.data['name'],
                description=request.data['description'],
                start_date=request.data['start_date'],
                end_date=request.data['end_date']
            )
            for chc in request.data['choices']:
                choice = Choice.objects.create(
                    name=chc,
                    poll=poll
                )
            return Response(status=201)
    except:
        return Response(status=400)

@api_view(['GET'])
@permission_classes((IsAuthenticated, IsRoleUserOrAdmin))
def get_polls(request):
    try:
        polls = Poll.objects.all()
        serializer = PollSerializer(polls, many=True)
        return Response(status=200, data = {'polls': serializer.data})
    except:
        return Response(status=400)

 
@api_view(['POST'])
@permission_classes((IsAuthenticated, IsRoleUserOrAdmin))
def cast_vote(request):
    try:
        with transaction.atomic():
            choice = Choice.objects.get(id=request.data['choice_id'])
            print(choice)
            # Make sure the date is between start and end date for poll
            if not (choice.poll.start_date <= localtime(now()) <= choice.poll.end_date):
                raise Exception('Date is not within the poll start and end date')
            choice.votes += 1
            choice.save()
            Blockchain.add_vote_to_block(request.user, choice)
            return Response(status=201)
    except IntegrityError:
        return Response(status=400, data={'message': 'You have already voted'})
    except Exception as e:
        print(e)
        return Response(status=400)