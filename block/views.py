from rest_framework.decorators import permission_classes, api_view
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from authentication.role_authentication import IsRoleAdmin, IsRoleUser, IsRoleUserOrAdmin
from .models import Block
from vote.models import Vote
from .serializers import BlockSerializer, BlockVoteSerializer


# Create your views here.
@api_view(['GET'])
@permission_classes((IsAuthenticated, IsRoleUserOrAdmin))
def get_blocks(request):
    try:
        blocks = Block.objects.all()
        serializer = BlockSerializer(blocks, many=True)
        return Response(status=200, data = serializer.data)
    except:
        return Response(status=400)

@api_view(['GET'])
@permission_classes((IsAuthenticated, IsRoleUserOrAdmin))
def get_block_votes(request):
    try:
        block = Block.objects.get(height=request.query_params['height'])
        votes = Vote.objects.filter(block=block)
        serializer = BlockVoteSerializer(votes, many=True)
        return Response(status=200, data = serializer.data)
    except Exception as e:
        print(e)
        return Response(status=400)