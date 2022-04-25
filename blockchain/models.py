from block.models import Block
from vote.models import Vote
from datetime import datetime

class Blockchain:
    @staticmethod
    def get_last_unverified_block(create_new_block_if_none_found=False):
        try:
            last = Block.objects.get(status=0)
            return last
        except Block.DoesNotExist:
            if create_new_block_if_none_found:
                blocks = Block.objects.all()
                print(blocks)
                last_block_height = -1
                prev_hash = 0
                if blocks.count() > 0:
                    last_block_height = blocks[len(blocks) - 1].height
                    prev_hash = blocks[len(blocks) - 1].hash
                last = Block.objects.create(
                    timestamp=datetime.now(),
                    height=last_block_height+1,
                    hash='',
                    previous_hash=prev_hash,
                    status=0,
                    nounce=0
                )
                return last
            raise None
        except Exception as e:
            raise e
    
    @staticmethod
    def add_vote_to_block(user, choice):
        try:
            last_block = Blockchain.get_last_unverified_block(create_new_block_if_none_found=True)
            vote = Vote.objects.create(
                user=user,
                choice=choice,
                poll=choice.poll,
                block=last_block
            )
            return vote
        except Exception as e:
            raise e
