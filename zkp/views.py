from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from authentication.role_authentication import IsRoleUserOrAdmin

from .models import ZKPObject

from random import randint
# Create your views here.

@api_view(['POST'])
@permission_classes((IsRoleUserOrAdmin,))
def zkp_first(request):
    """
    Gets h, y from client, generates random b (0/1) stores all three of them
    in the database and returns them to the client.
    """
    try:
        h = request.data.get('h')
        y = request.data.get('y')
        if h is None or y is None:
            return Response({'error': 'Missing parameters'})
        b = randint(0, 1)
        zkp_object = ZKPObject(h=h, y=y, b=b)
        zkp_object.save()
        return Response({'h': h, 'y': y, 'b': b, 'zkp_id': zkp_object.id})
    except:
        return Response(status=400)

@api_view(['POST'])
@permission_classes((IsRoleUserOrAdmin,))
def zkp_second(request):
    """
    Client sends zkp_id and s = (r + bx)mod10
    Server computes 2^s mod 11 & hy^b mod 11 and compares them
    """
    try:
        zkp_id = int(request.data.get('zkp_id'))
        s = int(request.data.get('s'))
        if zkp_id is None or s is None:
            return Response({'error': 'Missing parameters'})
        zkp_object = ZKPObject.objects.get(id=zkp_id)
        if zkp_object is None:
            return Response({'error': 'ZKP object not found'})
        b = zkp_object.b
        h = zkp_object.h
        y = zkp_object.y
        first_to_compare = (2 ** s) % 11
        second_to_compare = (h * (y ** b)) % 11
        if first_to_compare == second_to_compare:
            return Response(status=200)
        else:
            return Response(status=403)
    except Exception as e:
        print(e)
        return Response(status=400)
