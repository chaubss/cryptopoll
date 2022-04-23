from rest_framework.response import Response
from rest_framework.decorators import api_view
from .miner import start_miner as miner_start

# Create your views here.

@api_view(['POST'])
def start_miner(request):
    try:
        miner_start()
        return Response(status=200)
    except Exception as e:
        return Response(status=400, data={'message': str(e)})
