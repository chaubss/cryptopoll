import json
from sqlite3 import Timestamp
import time
import threading
import hashlib
from blockchain.models import Blockchain
from vote.models import Vote
from block.models import Block
from vote.serializer import VoteSerializer

def mine_block():
    print('Started mining')
    last_block = Blockchain.get_last_unverified_block()
    if last_block is None:
        raise Exception('No unverified block found')
    votes = Vote.objects.filter(block=last_block)

    last_block.status = 1
    last_block.save()
    
    if len(votes) == 0:
        return
    votes_data = json.dumps(VoteSerializer(votes, many=True).data)
    votes_hash = hashlib.sha256(votes_data.encode('utf-8')).hexdigest()

    prev_block_hash = '0' 
    if last_block.height != 0:
        prev_block_hash = Block.objects.get(height=last_block.height-1).hash


    final_str_pre = f'{last_block.timestamp} {last_block.height} {prev_block_hash} {votes_hash} '
    nounce = 0
    hex_dig = ''
    while True:
        final_str = f'{final_str_pre}{nounce}'
        hex_dig = hashlib.sha256(final_str.encode('utf-8')).hexdigest()
        if hex_dig[:5] == '00000':
            break
        nounce += 1
    
    print('Found nounce: ', nounce)
    print(final_str)
    last_block.nounce = nounce
    last_block.hash = hex_dig
    last_block.status = 2
    last_block.save()

    print('Finished mining')

def start_miner():
    ls = threading.enumerate()
    for thread in ls:
        if thread.name == 'miner':
            raise Exception('Miner already running')
            return
    try:
        thread = threading.Thread(target=mine_block, name='miner')
        thread.start()
        print('Miner started')
    except Exception as e:
        raise e